package com.andrev133.habittracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrev133.habittracker.databinding.ActivityHabitListBinding
import com.andrev133.habittracker.usecase.GetAllHabitsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HabitListActivity : AppCompatActivity() {
    private var _binding: ActivityHabitListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHabitListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val getAllHabitsUseCase = GetAllHabitsUseCase()
        binding.habitList.apply {
            layoutManager = LinearLayoutManager(this@HabitListActivity)
            adapter = HabitListAdapter()

            lifecycleScope.launch {
                getAllHabitsUseCase(this@HabitListActivity).collect { habits ->
                    (adapter as HabitListAdapter).updateData(habits)
                }
            }
        }

        binding.habitListFab.setOnClickListener {
            startActivity(
                Intent(this, HabitEditorActivity::class.java)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



val dataSet = mutableListOf<HabitModel>().apply {
    add(
        HabitModel(
            name = "Study",
            description = "Learn Kotlin",
            priority = 1,
            type = "Habit",
            color = Color.BLUE,
            periodicity = "Daily"
        )
    )
    add(
        HabitModel(
            name = "Exercise",
            description = "Run for 30 minutes",
            priority = 1,
            type = "Habit",
            color = Color.RED,
            periodicity = "Daily"
        )
    )
    add(
        HabitModel(
            name = "Meditate",
            description = "Meditate for 10 minutes",
            priority = 1,
            type = "Habit",
            color = Color.GREEN,
            periodicity = "Daily"
        )
    )
    add(
        HabitModel(
            name = "Read",
            description = "Read a book",
            priority = 1,
            type = "Habit",
            color = Color.YELLOW,
            periodicity = "Daily"
        )
    )
}