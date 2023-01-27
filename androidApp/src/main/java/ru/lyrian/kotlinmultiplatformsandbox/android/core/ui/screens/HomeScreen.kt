package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation.BottomNavigationBar
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs.HomeNavGraph

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        HomeNavGraph(
            navController = navController,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}
