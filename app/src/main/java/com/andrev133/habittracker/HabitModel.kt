package com.andrev133.habittracker

data class HabitModel(
    val name: String,
    val priority: Int,
    val periodicity: String,
    val type: String,
    val description: String,
    val color: Int,
    var isExpanded: Boolean = false
)