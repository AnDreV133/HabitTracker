package com.andrev133.habittracker

import java.util.UUID

data class HabitModel(
    val name: String,
    val priority: Int,
    val periodicity: String,
    val type: String,
    val description: String,
    val color: Int,
    val uuid: UUID? = UUID.randomUUID(),
    var isExpanded: Boolean = false
)