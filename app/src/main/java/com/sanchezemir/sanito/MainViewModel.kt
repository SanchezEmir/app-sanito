package com.sanchezemir.sanito

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanchezemir.sanito.authentication.domain.usecase.GetUserIdUseCase
import com.sanchezemir.sanito.authentication.domain.usecase.LogoutUseCase
import com.sanchezemir.sanito.onboarding.domain.usecase.HasSeenOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hasSeenOnboardingUseCase: HasSeenOnboardingUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    var hasSeenOnboarding by mutableStateOf(hasSeenOnboardingUseCase())
        private set
    var isLoggedIn by mutableStateOf(getUserIdUseCase() != null)
        private set

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}