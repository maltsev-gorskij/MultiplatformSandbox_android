package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation.BottomNavItems
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.NavDestinations
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.ui.LaunchesListScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = NavGraphsDestinations.HOME,
        startDestination = BottomNavItems.Launches.route
    ) {
        composable(NavDestinations.HomeNavGraph.LAUNCHES) {
            LaunchesListScreen(
                onLaunchClick = { id ->
                    navController.navigateToLaunchDetails(id)
                }
            )
        }
        composable(NavDestinations.HomeNavGraph.FAVORITES) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Favorites placeholder"
                    )
                }
            }
        }
        composable(BottomNavItems.Profile.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Profile placeholder"
                    )
                }
            }
        }
        detailsNavGraph(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
