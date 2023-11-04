package com.sanchezemir.sanito.authentication.domain.usecase

import com.sanchezemir.sanito.authentication.domain.repository.AuthenticationRepository

class GetUserIdUseCase(private val repository: AuthenticationRepository) {
    operator fun invoke(): String? {
        return repository.getUserId()
    }
}