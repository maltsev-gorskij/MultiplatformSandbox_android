package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.details.presentation.model

sealed interface LaunchDetailsEvent {
    data class ShowErrorMessage(val message: String) : LaunchDetailsEvent
}
