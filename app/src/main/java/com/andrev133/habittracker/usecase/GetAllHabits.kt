package com.andrev133.habittracker.usecase

import com.andrev133.habittracker.HabitModel
import kotlinx.coroutines.flow.Flow

interface GetAllHabits {
    operator fun invoke(): Flow<List<HabitModel>>

    fun refresh()
}