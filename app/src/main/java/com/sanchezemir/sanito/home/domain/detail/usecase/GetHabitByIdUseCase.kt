package com.sanchezemir.sanito.home.domain.detail.usecase

import com.sanchezemir.sanito.home.domain.models.Habit
import com.sanchezemir.sanito.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHabitByIdUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(id: String): Habit {
        return withContext(Dispatchers.IO) {
            repository.getHabitById(id)
        }
    }
}
