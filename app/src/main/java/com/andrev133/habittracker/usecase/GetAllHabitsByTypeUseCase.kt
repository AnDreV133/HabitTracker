package com.andrev133.habittracker.usecase

import android.content.Context
import com.andrev133.habittracker.HabitModel
import com.andrev133.habittracker.TypeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllHabitsByTypeUseCase(context: Context, private val type: TypeEnum) : GetAllHabits {
    private val getAllHabitsUseCase = GetAllHabitsUseCase(context)

    override fun invoke(): Flow<List<HabitModel>> = getAllHabitsUseCase.invoke().map { habits ->
        habits.filter { it.type == type }
    }

    override fun refresh() = getAllHabitsUseCase.refresh()
}