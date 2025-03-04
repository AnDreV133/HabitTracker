package com.andrev133.habittracker.usecase

import android.annotation.SuppressLint
import android.content.Context
import com.andrev133.habittracker.HabitModel
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class SaveHabitUseCase {
    @SuppressLint("ApplySharedPref")
    operator fun invoke(context: Context, model: HabitModel, onEndSave: () -> Unit) {
        val json = model.toJson().toString()
        val uuid = model.uuid ?: UUID.randomUUID()

        CoroutineScope(Dispatchers.IO).launch {
            context.getSharedPreferences("habit_${uuid}", Context.MODE_PRIVATE)
                .edit()
                .putString("JSON", json)
                .commit()

            onEndSave()
        }
    }

    private fun HabitModel.toJson() = JsonObject().apply {
        addProperty("name", name)
        addProperty("priority", priority)
        addProperty("quantity", quantity)
        addProperty("periodicity", periodicity)
        addProperty("type", type.toString())
        addProperty("description", description)
        addProperty("color", color)
    }
}