package com.sanchezemir.sanito.onboarding.domain.usecase

import com.sanchezemir.sanito.onboarding.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke() {
        repository.completeOnboarding()
    }
}
