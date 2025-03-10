package com.andrev133.habittracker.usecase

import android.content.Context
import com.andrev133.habittracker.HabitModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

class GetAllHabitsUseCase(private val context: Context): GetAllHabits {
    private val getHabitUseCase = GetHabitUseCase(context)
    private val flow = MutableSharedFlow<List<HabitModel>>(replay = 1)

    override operator fun invoke(): Flow<List<HabitModel>> {
        refresh()
        return flow
    }

    override fun refresh() {
        CoroutineScope(Dispatchers.IO).launch {
            flow.emit(loadHabits())
        }
    }

    private suspend fun loadHabits(): List<HabitModel> =
        withContext(Dispatchers.IO) {
            val sharedPrefsDir = File(context.applicationInfo.dataDir, "shared_prefs")
            if (!sharedPrefsDir.exists() || !sharedPrefsDir.isDirectory) {
                return@withContext emptyList()
            }

            val uuids = sharedPrefsDir.listFiles { _, name ->
                name.startsWith("habit_") && name.endsWith(".xml")
            }?.map { file ->
                file.name.removePrefix("habit_").removeSuffix(".xml")
            } ?: emptyList()

            val deferredResult = withContext(Dispatchers.IO) {
                uuids.map { uuid ->
                    async { getHabitUseCase(UUID.fromString(uuid)).also { println(it) } }
                }
            }

            deferredResult
                .awaitAll()
                .filterNotNull()
        }
}