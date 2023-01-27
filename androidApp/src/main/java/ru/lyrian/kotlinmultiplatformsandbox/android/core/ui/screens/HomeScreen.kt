package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation.BottomNavigationBar
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs.HomeNavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        HomeNavGraph(navController)
    }
}
