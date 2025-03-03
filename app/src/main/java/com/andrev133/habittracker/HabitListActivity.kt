package com.andrev133.habittracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrev133.habittracker.databinding.ActivityHabitListBinding
import com.andrev133.habittracker.usecase.GetAllHabitsUseCase
import kotlinx.coroutines.launch

class HabitListActivity : AppCompatActivity() {
    private var _binding: ActivityHabitListBinding? = null
    private val binding get() = _binding!!
    private var _getAllHabitsUseCase: GetAllHabitsUseCase? = null
    private val getAllHabitsUseCase get() = _getAllHabitsUseCase!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHabitListBinding.inflate(layoutInflater)
        _getAllHabitsUseCase = GetAllHabitsUseCase(this)

        setContentView(binding.root)

        binding.habitList.apply {
            layoutManager = LinearLayoutManager(this@HabitListActivity)
            adapter = HabitListAdapter()

            lifecycleScope.launch {
                getAllHabitsUseCase().collect { habits ->
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

    override fun onResume() {
        super.onResume()
        getAllHabitsUseCase.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _getAllHabitsUseCase = null
    }
}