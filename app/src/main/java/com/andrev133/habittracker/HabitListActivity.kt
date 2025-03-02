package com.andrev133.habittracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrev133.habittracker.databinding.ActivityHabitListBinding

class HabitListActivity : AppCompatActivity() {
    private var _binding: ActivityHabitListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_habit_list)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

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

        _binding = ActivityHabitListBinding.inflate(layoutInflater)
        binding.run {
            setContentView(root)
            habitList.layoutManager = LinearLayoutManager(this@HabitListActivity)
            habitList.adapter = HabitListAdapter(dataSet)
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