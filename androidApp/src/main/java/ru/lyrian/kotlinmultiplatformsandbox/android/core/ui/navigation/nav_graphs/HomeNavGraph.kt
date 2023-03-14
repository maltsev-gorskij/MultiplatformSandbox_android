package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation.BottomNavItems
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.LocalScaffoldPaddings
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.ui.LaunchesListScreen
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.ui.ProfileScreen
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.videos.list.presentation.ui.VideosScreen

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
                modifier = Modifier.fillMaxSize().padding(LocalScaffoldPaddings.current),
                onLaunchClick = {
                    navController.navigate("${HomeGraph.LaunchesDetailsGraph.DETAILS}/$it")
                }
            )
        }

        launchDetailsNavGraph(
            onNavigateBack = { navController.popBackStack() },
            onWatchVideoClick = { navController.navigate("${HomeGraph.VideoDetailsGraph.DETAILS}/$it") }
        )

        composable(HomeGraph.VIDEOS) {
            VideosScreen(
                modifier = Modifier.fillMaxSize().padding(LocalScaffoldPaddings.current),
                onFullScreenButtonClick = {
                    navController.navigate("${HomeGraph.VideoDetailsGraph.DETAILS}/$it")
                },
            )
        }

        videoDetailsNavGraph(
            onNavigateBack = { navController.popBackStack() }
        )

        composable(HomeGraph.PROFILE) {
            ProfileScreen(
                modifier = Modifier.fillMaxSize().padding(LocalScaffoldPaddings.current),
            )
        }
    }
}
