package com.sanchezemir.sanito.authentication.domain.usecase

data class SignupUseCases(
    val signupWithEmailUseCase: SignupWithEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase
)
