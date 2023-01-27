package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.model

sealed interface LaunchDetailsEvent {
    data class ShowErrorMessage(val message: String) : LaunchDetailsEvent
}
