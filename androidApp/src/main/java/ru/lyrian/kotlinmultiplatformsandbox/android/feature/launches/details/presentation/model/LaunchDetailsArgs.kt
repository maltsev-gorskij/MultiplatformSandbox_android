package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.details.presentation.model

import androidx.lifecycle.SavedStateHandle
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph.LaunchesDetailsGraph

class LaunchDetailsArgs(val launchId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                launchId = checkNotNull(
                    savedStateHandle[LaunchesDetailsGraph.DetailsArgs.LAUNCH_ID]
                ) as String
            )
}
