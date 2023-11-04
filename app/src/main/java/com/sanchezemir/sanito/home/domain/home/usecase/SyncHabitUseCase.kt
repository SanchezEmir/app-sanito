package com.sanchezemir.sanito.home.domain.home.usecase

import com.sanchezemir.sanito.home.domain.repository.HomeRepository

class SyncHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() {
        repository.syncHabits()
    }
}