package com.andrev133.habittracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.andrev133.habittracker.databinding.ActivityHabitListBinding

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

        binding.habitListContainer.apply {
            adapter = HabitListFragmentAdapter(requireActivity())
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.bottomNavigation.menu.getItem(position).isChecked = true
                }
            })
        }
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

    override fun onResume() {
        super.onResume()
        // todo: method for update data into fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HabitListFragment()
    }

}