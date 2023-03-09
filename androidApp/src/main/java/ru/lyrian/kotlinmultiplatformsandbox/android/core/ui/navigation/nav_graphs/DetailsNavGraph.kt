package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph.LaunchesDetailsGraph
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.NavDestinationsArgs.Details.LAUNCH_ID_ARG
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.ui.LaunchDetailsScreen

fun NavGraphBuilder.detailsNavGraph(
    onNavigateBack: () -> Unit
) {
    navigation(
        route = LaunchesDetailsGraph.GRAPH_ROUTE,
        startDestination = LaunchesDetailsGraph.DETAILS
    ) {
        composable("${LaunchesDetailsGraph.DETAILS}/{$LAUNCH_ID_ARG}") {
            LaunchDetailsScreen(
                onNavigateBackClick = onNavigateBack,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}