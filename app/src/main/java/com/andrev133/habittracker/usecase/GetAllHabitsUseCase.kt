package com.andrev133.habittracker.usecase

import android.content.Context
import com.andrev133.habittracker.HabitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class GetAllHabitsUseCase {
    operator fun invoke(context: Context): Flow<List<HabitModel>> = flow {
        val sharedPrefsDir = File(context.applicationInfo.dataDir, "shared_prefs")
        if (!sharedPrefsDir.exists() || !sharedPrefsDir.isDirectory) {
            emit(emptyList())
            return@flow
        }

        val uuids = sharedPrefsDir.listFiles { _, name ->
            name.startsWith("habit_") && name.endsWith(".xml")
        }?.map { file ->
            file.name.removePrefix("habit_").removeSuffix(".xml")
        } ?: emptyList()

        val deferredResult = withContext(Dispatchers.IO) {
            val getHabitUseCase = GetHabitUseCase()
            uuids.map { uuid ->
                async { getHabitUseCase(context, UUID.fromString(uuid)).also { println(it) } }
            }
        }

        deferredResult
            .awaitAll()
            .filterNotNull()
            .let { list -> emit(list) }
    }
}