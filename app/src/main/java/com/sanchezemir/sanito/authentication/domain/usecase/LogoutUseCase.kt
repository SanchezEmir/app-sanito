package com.sanchezemir.sanito.authentication.domain.usecase

import com.sanchezemir.sanito.authentication.domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}
