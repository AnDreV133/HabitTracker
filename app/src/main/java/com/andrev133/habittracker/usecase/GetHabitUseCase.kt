package com.andrev133.habittracker.usecase

import android.content.Context
import com.andrev133.habittracker.HabitModel
import com.andrev133.habittracker.TypeEnum
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.util.UUID

class GetHabitUseCase {
    operator fun invoke(context: Context, uuid: UUID) =
        context.getSharedPreferences("habit_${uuid}", Context.MODE_PRIVATE)
            .getString("JSON", null)
            ?.let { jsonStr -> JsonParser.parseString(jsonStr).getAsJsonObject() }
            ?.toModel(uuid)

    private fun JsonObject.toModel(uuid: UUID? = null) = HabitModel(
        name = get("name").asString,
        priority = get("priority").asInt,
        quantity = get("quantity").asString,
        periodicity = get("periodicity").asString,
        type = TypeEnum.valueOf(get("type").asString),
        description = get("description").asString,
        color = get("color").asInt,
        uuid = uuid
    )
}