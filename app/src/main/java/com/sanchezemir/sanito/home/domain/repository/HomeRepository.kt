package com.sanchezemir.sanito.home.domain.repository

import com.sanchezemir.sanito.home.domain.models.Habit
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface HomeRepository {
    fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit)
    suspend fun getHabitById(id: String): Habit
    suspend fun syncHabits()
}
