package com.andrev133.habittracker.usecase

import android.content.Context
import com.andrev133.habittracker.HabitModel
import com.google.gson.JsonObject
import java.util.UUID

class SaveHabitUseCase {
    operator fun invoke(context: Context, model: HabitModel) {
        val json = model.toJson().toString()
        val uuid = model.uuid ?: UUID.randomUUID()

        context.getSharedPreferences("habit_${uuid}", Context.MODE_PRIVATE)
            .edit()
            .putString("JSON", json)
            .apply()
    }

    private fun HabitModel.toJson() = JsonObject().apply {
        addProperty("name", name)
        addProperty("priority", priority)
        addProperty("periodicity", periodicity)
        addProperty("type", type)
        addProperty("description", description)
        addProperty("color", color)
    }
}