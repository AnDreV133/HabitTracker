package com.andrev133.habittracker

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andrev133.habittracker.databinding.ActivityHabitEditorBinding
import com.andrev133.habittracker.usecase.GetHabitUseCase
import com.andrev133.habittracker.usecase.SaveHabitUseCase
import java.util.UUID

class HabitEditorActivity : AppCompatActivity() {
    private var _binding: ActivityHabitEditorBinding? = null
    private val binding get() = _binding!!
    private var _saveHabitUseCase: SaveHabitUseCase? = null
    private val saveHabitUseCase get() = _saveHabitUseCase!!
    private var _getHabitUseCase: GetHabitUseCase? = null
    private val getHabitUseCase get() = _getHabitUseCase!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHabitEditorBinding.inflate(layoutInflater)
        _saveHabitUseCase = SaveHabitUseCase()
        _getHabitUseCase = GetHabitUseCase()

        setContentView(binding.root)

        binding.habitEditorPrioritySpinner.apply {
            adapter = ArrayAdapter.createFromResource(
                this@HabitEditorActivity,
                R.array.priority_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(binding.habitEditorPrioritySpinner.adapter.count - 1)
        }

        binding.habitEditorTypeRadioGroup.check(R.id.habitEditorTypeNeutral)

        binding.habitEditorSaveButton.setOnClickListener {
            saveHabitUseCase(this, binding.toModel())
            finish() // todo or switch by intent
        }

        intent?.getStringExtra(EXTRA_UUID)?.let { uuid ->
            getHabitUseCase(this, UUID.fromString(uuid))?.let { model ->
//                binding.bind(model)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _saveHabitUseCase = null
        _getHabitUseCase = null
    }

    private fun ActivityHabitEditorBinding.toModel(uuid: UUID? = null) = HabitModel(
        name = habitEditorNameEditText.text.toString(),
        priority = habitEditorPrioritySpinner.selectedItemPosition,
        quantity = habitEditorQuantityEditText.text.toString(),
        periodicity = habitEditorPeriodicityEditText.text.toString(),
        type = habitEditorTypeRadioGroup.checkedRadioButtonId.toTypeEnum(),
        description = habitEditorDescriptionEditText.text.toString(),
        color = 0, // todo
        uuid = uuid,
    )

//    private fun ActivityHabitEditorBinding.bind(model: HabitModel) = binding.run {
//        habitEditorNameEditText.setText(model.name)
//        habitEditorDescriptionEditText.setText(model.description)
//        habitEditorPrioritySpinner.setSelection()
//        habitEditorTypeRadioGroup.check()
//        habitEditorQuantityEditText.setText()
//        habitEditorPeriodicityEditText.setText()
//    }

    companion object ExtraConst {
        const val EXTRA_UUID = "EXTRA_HABIT_UUID"
    }
}