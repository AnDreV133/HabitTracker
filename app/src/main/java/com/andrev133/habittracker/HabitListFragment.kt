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

class HabitListFragment : Fragment() {
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

        binding.habitListContainer.adapter = HabitListFragmentAdapter(requireActivity())

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            binding.habitListContainer.currentItem = when (menuItem.itemId) {
                R.id.menuItemGood -> 0
                R.id.menuItemBad -> 1
                else -> throw RuntimeException("Unknown menu item")
            }

            true
        }

        binding.habitListFab.setOnClickListener {
            (activity as OpenHabitEditorFragmentByUuid).openHabitEditorFragmentByUuid(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HabitListFragment()
    }

}