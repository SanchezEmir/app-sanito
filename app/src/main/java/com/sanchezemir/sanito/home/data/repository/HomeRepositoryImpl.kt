package com.sanchezemir.sanito.home.data.repository

import androidx.work.*
import com.sanchezemir.sanito.home.data.extension.toStartOfDateTimestamp
import com.sanchezemir.sanito.home.data.local.HomeDao
import com.sanchezemir.sanito.home.data.mapper.toDomain
import com.sanchezemir.sanito.home.data.mapper.toDto
import com.sanchezemir.sanito.home.data.mapper.toEntity
import com.sanchezemir.sanito.home.data.mapper.toSyncEntity
import com.sanchezemir.sanito.home.data.remote.HomeApi
import com.sanchezemir.sanito.home.data.remote.util.resultOf
import com.sanchezemir.sanito.home.data.sync.HabitSyncWorker
import com.sanchezemir.sanito.home.domain.alarm.AlarmHandler
import com.sanchezemir.sanito.home.domain.models.Habit
import com.sanchezemir.sanito.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.*
import java.time.Duration
import java.time.ZonedDateTime

class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workManager: WorkManager
) : HomeRepository {
    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { it.toDomain() } }
        val apiFlow = getHabitsFromApi()

        return localFlow.combine(apiFlow) { db, _ ->
            db
        }
    }

    private fun getHabitsFromApi(): Flow<List<Habit>> {
        return flow {
            resultOf {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            }
            emit(emptyList<Habit>())
        }.onStart {
            emit(emptyList())
        }
    }

    override suspend fun insertHabit(habit: Habit) {
        handleAlarm(habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }.onFailure {
            dao.insertHabitSync(habit.toSyncEntity())
        }
    }

    private suspend fun insertHabits(habits: List<Habit>) {
        habits.forEach {
            handleAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    private suspend fun handleAlarm(habit: Habit) {
        try {
            val previous = dao.getHabitById(habit.id)
            alarmHandler.cancel(previous.toDomain())
        } catch (e: Exception) { /* Habit doesn't exist */
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }

    override suspend fun syncHabits() {
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_habit_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }
}
