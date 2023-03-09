package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.NavDestinations
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.HomeScreen

@Composable
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = NavDestinations.GraphsDestinations.ROOT,
        startDestination = NavDestinations.GraphsDestinations.HOME
    ) {
        composable(route = NavDestinations.GraphsDestinations.HOME) {
            HomeScreen()
        }
    }
}
