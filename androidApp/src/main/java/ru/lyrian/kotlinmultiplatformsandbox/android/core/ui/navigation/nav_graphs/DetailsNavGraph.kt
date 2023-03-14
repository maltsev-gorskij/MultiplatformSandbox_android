package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph.LaunchesDetailsGraph
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.LocalScaffoldPaddings
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.details.presentation.ui.LaunchDetailsScreen

fun NavGraphBuilder.launchDetailsNavGraph(
    onNavigateBack: () -> Unit,
    onWatchVideoClick: (String) -> Unit
) {
    navigation(
        route = LaunchesDetailsGraph.GRAPH_ROUTE,
        startDestination = LaunchesDetailsGraph.DETAILS
    ) {
        composable("${LaunchesDetailsGraph.DETAILS}/{${LaunchesDetailsGraph.DetailsArgs.LAUNCH_ID}}") {
            LaunchDetailsScreen(
                onNavigateBackClick = onNavigateBack,
                onWatchVideoClick = onWatchVideoClick,
                modifier = Modifier.fillMaxSize().padding(LocalScaffoldPaddings.current),
            )
        }
    }
}