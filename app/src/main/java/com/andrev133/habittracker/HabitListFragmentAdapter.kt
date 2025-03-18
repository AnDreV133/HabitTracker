package com.andrev133.habittracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HabitListFragmentAdapter(private val fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SubHabitListFragment.newInstanceTypeGood(fa.baseContext)
        1 -> SubHabitListFragment.newInstanceTypeBad(fa.baseContext)
        else -> throw IllegalArgumentException("No fragment at position: $position")
    }
}