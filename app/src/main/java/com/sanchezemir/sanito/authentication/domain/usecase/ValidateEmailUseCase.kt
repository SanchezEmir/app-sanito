package com.sanchezemir.sanito.authentication.domain.usecase

import com.sanchezemir.sanito.authentication.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}
