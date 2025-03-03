package com.andrev133.habittracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andrev133.habittracker.databinding.ActivityHabitEditorBinding
import com.andrev133.habittracker.usecase.SaveHabitUseCase
import java.util.UUID

class HabitEditorActivity : AppCompatActivity() {
    private var _binding: ActivityHabitEditorBinding? = null
    private val binding get() = _binding!!

    private val saveHabitUseCase = SaveHabitUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHabitEditorBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.habitEditorPrioritySpinner.run {
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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun ActivityHabitEditorBinding.toModel(uuid: UUID? = null) = HabitModel(
        name = habitEditorNameEditText.text.toString(),
        priority = habitEditorPrioritySpinner.selectedItem?.toString()?.toInt() ?: 4,
        periodicity = habitEditorQuantityEditText.text.toString() + "/" + habitEditorFrequencyEditText.text.toString(),
        type = root.findViewById<RadioButton>(habitEditorTypeRadioGroup.checkedRadioButtonId).text.toString(),
        description = habitEditorDescriptionEditText.text.toString(),
        color = 0, // todo
        uuid = uuid,
    )
}