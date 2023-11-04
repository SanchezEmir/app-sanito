package com.sanchezemir.sanito.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.sanchezemir.sanito.R
import com.sanchezemir.sanito.onboarding.presentation.components.OnboardingPager

@Composable
fun OnboardingScreen(
    viewmodel: OnboardingViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    LaunchedEffect(key1 = viewmodel.hasSeenOnboarding) {
        if (viewmodel.hasSeenOnboarding) {
            onFinish()
        }
    }

    val pages = listOf(
        OnboardingPagerInformation(
            title = "Welcome to Sanito Application",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding1
        ),
        OnboardingPagerInformation(
            title = "Lorem Ipsum is simply dummy",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding2
        ),
        OnboardingPagerInformation(
            title = "Lorem Ipsum is simply dummy",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding3
        ),
        OnboardingPagerInformation(
            title = "Lorem Ipsum is simply dummy",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding4
        )
    )

    OnboardingPager(pages = pages, onFinish = {
        viewmodel.completeOnboarding()
    })
}
