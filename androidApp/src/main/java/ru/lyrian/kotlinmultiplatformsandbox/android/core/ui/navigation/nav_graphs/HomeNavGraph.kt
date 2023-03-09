package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation.BottomNavItems
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.ui.LaunchesListScreen
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_media.presentation.ui.LaunchesMediaScreen
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.ui.ProfileScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        route = HomeGraph.GRAPH_ROUTE,
        startDestination = BottomNavItems.Launches.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(HomeGraph.LAUNCHES) {
            LaunchesListScreen(
                onLaunchClick = {
                    navController.navigate("${HomeGraph.LaunchesDetailsGraph.DETAILS}/$it")
                }
            )
        }
        composable(HomeGraph.VIDEOS) {
            LaunchesMediaScreen(
                modifier = Modifier.fillMaxSize(),
                onFullScreenButtonClick = {
                    TODO("Perform navigate to full screen composable")
                },
            )
        }
        composable(HomeGraph.PROFILE) {
            ProfileScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        detailsNavGraph(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
