package com.andrev133.habittracker.usecase

import android.content.Context
import com.andrev133.habittracker.HabitModel
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.util.UUID

class GetHabitUseCase {
    operator fun invoke(context: Context, uuid: UUID) =
        context.getSharedPreferences("habit_${uuid}", Context.MODE_PRIVATE)
            .getString("JSON", null).also { println("json string " + it) }
            ?.let { jsonStr -> JsonParser.parseString(jsonStr).getAsJsonObject() }.also { println("json " + it) }
            ?.toModel(uuid).also { println("model " + it) }

    private fun JsonObject.toModel(uuid: UUID? = null) = HabitModel(
        name = get("name").asString,
        priority = get("priority").asInt,
        periodicity = get("periodicity").asString,
        type = get("type").asString,
        description = get("description").asString,
        color = get("color").asInt,
        uuid = uuid
    )
}