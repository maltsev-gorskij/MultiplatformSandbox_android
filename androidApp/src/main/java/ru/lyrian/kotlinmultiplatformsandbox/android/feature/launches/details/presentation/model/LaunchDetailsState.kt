package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.details.presentation.model

import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

data class LaunchDetailsState(
    val launch: RocketLaunch? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
