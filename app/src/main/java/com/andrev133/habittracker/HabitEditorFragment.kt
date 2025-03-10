package com.andrev133.habittracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.andrev133.habittracker.databinding.ActivityHabitEditorBinding
import com.andrev133.habittracker.usecase.GetHabitUseCase
import com.andrev133.habittracker.usecase.SaveHabitUseCase
import java.util.UUID

class HabitEditorFragment(
    private val uuid: UUID?,
    private val saveHabitUseCase: SaveHabitUseCase,
    private val getHabitUseCase: GetHabitUseCase
) : Fragment(), ColorPickerPopup.OnColorSelectedListener {
    private var _binding: ActivityHabitEditorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityHabitEditorBinding
            .inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.habitEditorPrioritySpinner.apply {
            adapter = ArrayAdapter.createFromResource(
                context,
                R.array.priority_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(binding.habitEditorPrioritySpinner.adapter.count - 1)
        }

        binding.habitEditorTypeRadioGroup.check(R.id.habitEditorTypeNeutral)

        binding.habitEditorSaveButton.setOnClickListener {
            saveHabitUseCase(binding.toModel(uuid)) {
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this@HabitEditorFragment)
                    ?.commit()
            }
        }

        var isRgbTextProgrammaticChange = false
        var isHsvTextProgrammaticChange = false
        binding.habitEditorColorRgbEditText.addTextChangedListener { text ->
            if (isRgbTextProgrammaticChange) return@addTextChangedListener
            try {
                Color.parseColor(text.toString())
            } catch (e: Exception) {
                null
            }?.let { color ->
                binding.habitEditorColorPicker.backgroundTintList = ColorStateList.valueOf(color)
                isHsvTextProgrammaticChange = true
                binding.habitEditorColorHsvEditText.setText(color.toHsvFmt())
                isHsvTextProgrammaticChange = false
            }
        }

        binding.habitEditorColorHsvEditText.addTextChangedListener { text ->
            if (isHsvTextProgrammaticChange) return@addTextChangedListener
            try {
                val hsv = text.toString().split(" ").map { it.toFloat() }
                Color.HSVToColor(hsv.toFloatArray())
            } catch (e: Exception) {
                if (e is IllegalArgumentException || e is RuntimeException) null
                else throw e
            }?.let { color ->
                binding.habitEditorColorPicker.backgroundTintList = ColorStateList.valueOf(color)
                isRgbTextProgrammaticChange = true
                binding.habitEditorColorRgbEditText.setText(color.toRgbFmt())
                isRgbTextProgrammaticChange = false
            }
        }

        binding.habitEditorColorPicker.setOnClickListener {
            val (colorPickerGlobalX, colorPickerGlobalY) = IntArray(2).also { arr ->
                binding.habitEditorColorPicker.getLocationOnScreen(arr)
            }
            ColorPickerPopup(binding).apply {
                setOnColorSelectedListener(this@HabitEditorFragment)
                showAtLocation(
                    binding.habitEditorColorPicker,
                    Gravity.TOP or Gravity.START,
                    colorPickerGlobalX,
                    colorPickerGlobalY
                )
            }
        }

        uuid?.let { uuid ->
            getHabitUseCase(uuid)?.let { model -> binding.bind(model) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun ActivityHabitEditorBinding.toModel(uuid: UUID?) = HabitModel(
        name = habitEditorNameEditText.text.toString(),
        priority = habitEditorPrioritySpinner.selectedItemPosition,
        quantity = habitEditorQuantityEditText.text.toString(),
        periodicity = habitEditorPeriodicityEditText.text.toString(),
        type = habitEditorTypeRadioGroup.checkedRadioButtonId.toTypeEnum(),
        description = habitEditorDescriptionEditText.text.toString(),
        color = habitEditorColorPicker.backgroundTintList?.defaultColor ?: 0xffffff,
        uuid = uuid,
    )

    private fun ActivityHabitEditorBinding.bind(model: HabitModel) {
        habitEditorNameEditText.setText(model.name)
        habitEditorDescriptionEditText.setText(model.description)
        habitEditorPrioritySpinner.setSelection(model.priority)
        habitEditorTypeRadioGroup.check(model.type.toResRadioBtn())
        habitEditorQuantityEditText.setText(model.quantity)
        habitEditorPeriodicityEditText.setText(model.periodicity)
        setColor(model.color)
    }

    private fun ActivityHabitEditorBinding.setColor(color: Int) {
        habitEditorColorPicker.backgroundTintList = ColorStateList.valueOf(color)
        habitEditorColorRgbEditText.setText(color.toRgbFmt())
        habitEditorColorHsvEditText.setText(color.toHsvFmt())
    }

    private fun Int.toRgbFmt() = "#%08x".format(this)

    private fun Int.toHsvFmt() = FloatArray(3)
        .also { arr -> Color.colorToHSV(this, arr) }
        .let { "%.2f %.2f %.2f".format(it[0], it[1], it[2]) }

    override fun onColorSelected(color: Int) {
        binding.setColor(color)
    }

    companion object {
        fun newInstance(
            context: Context,
            uuid: UUID?,
        ) = HabitEditorFragment(
            uuid,
            SaveHabitUseCase(context),
            GetHabitUseCase(context)
        )
    }
}