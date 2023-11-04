package com.sanchezemir.sanito.onboarding.domain.usecase

import com.sanchezemir.sanito.onboarding.domain.repository.OnboardingRepository

class HasSeenOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Boolean {
        return repository.hasSeenOnboarding()
    }
}
