package com.sanchezemir.sanito.home.domain.detail.usecase

import com.sanchezemir.sanito.home.domain.models.Habit
import com.sanchezemir.sanito.home.domain.repository.HomeRepository

class InsertHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(habit: Habit) {
        repository.insertHabit(habit)
    }
}
