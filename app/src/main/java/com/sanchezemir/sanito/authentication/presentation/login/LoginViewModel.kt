package com.sanchezemir.sanito.authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanchezemir.sanito.authentication.domain.usecase.*
import com.sanchezemir.sanito.authentication.presentation.util.PasswordErrorParser
import com.sanchezemir.sanito.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) :
    ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChange -> {
                state = state.copy(
                    email = event.email
                )
            }
            LoginEvent.Login -> {
                login()
            }
            is LoginEvent.PasswordChange -> {
                state = state.copy(
                    password = event.password
                )
            }
        }
    }

    private fun login() {
        state = state.copy(
            emailError = null,
            passwordError = null
        )
        if (!loginUseCases.validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = "El email no es valido"
            )
        }
        val passwordResult = loginUseCases.validatePasswordUseCase(state.password)
        state = state.copy(
            passwordError = PasswordErrorParser.parseError(passwordResult)
        )

        if (state.emailError == null && state.passwordError == null) {
            state = state.copy(
                isLoading = true
            )
            viewModelScope.launch(dispatcher) {
                loginUseCases.loginWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(
                        isLoggedIn = true
                    )
                }.onFailure {
                    state = state.copy(
                        emailError = it.message
                    )
                }
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }
}
