package com.andrev133.habittracker

import java.util.UUID

data class HabitModel(
    val name: String,
    val priority: Int, // resource id
    val quantity: String,
    val periodicity: String,
    val type: TypeEnum, // resource id
    val description: String,
    val color: Int, // colorInt
    val uuid: UUID? = UUID.randomUUID(),
    var isExpanded: Boolean = false
)