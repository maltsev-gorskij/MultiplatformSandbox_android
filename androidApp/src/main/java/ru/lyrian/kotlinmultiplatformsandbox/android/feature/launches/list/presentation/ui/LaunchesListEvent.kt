package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.ui

sealed interface LaunchesListEvent {
    data class ShowToast(val message: String): LaunchesListEvent
}
