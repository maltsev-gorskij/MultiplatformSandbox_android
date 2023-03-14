package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.HomeScreen

@Composable
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = Destinations.RootGraph.GRAPH_ROUTE,
        startDestination = Destinations.RootGraph.HomeGraph.GRAPH_ROUTE
    ) {
        composable(route = Destinations.RootGraph.HomeGraph.GRAPH_ROUTE) {
            HomeScreen()
        }
    }
}
