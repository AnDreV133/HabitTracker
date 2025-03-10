package com.andrev133.habittracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrev133.habittracker.databinding.ActivityHabitListBinding
import com.andrev133.habittracker.usecase.GetAllHabits
import com.andrev133.habittracker.usecase.GetAllHabitsByTypeUseCase
import kotlinx.coroutines.launch

class HabitListFragment(private val getAllHabitsUseCase: GetAllHabits) : Fragment() {
    private var _binding: ActivityHabitListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityHabitListBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.habitList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HabitListAdapter()
            initFlow(adapter as HabitListAdapter)
        }

        binding.habitListFab.setOnClickListener {
            (activity as OpenHabitEditorFragmentByUuid).openHabitEditorFragmentByUuid(null)
        }
    }

    override fun onResume() {
        super.onResume()
        getAllHabitsUseCase.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFlow(adapter: HabitListAdapter) = lifecycleScope.launch {
        getAllHabitsUseCase().collect { habits ->
            adapter.updateData(habits)
        }
    }

    companion object {
        fun newInstanceTypeGood(context: Context) =
            HabitListFragment(GetAllHabitsByTypeUseCase(context, TypeEnum.GOOD))

        fun newInstanceTypeBad(context: Context) =
            HabitListFragment(GetAllHabitsByTypeUseCase(context, TypeEnum.BAD))
    }
}