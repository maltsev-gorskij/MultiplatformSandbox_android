package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation.BottomNavigationBar
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs.HomeNavGraph

val LocalSnackBarState = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val scaffoldState = rememberScaffoldState()

    CompositionLocalProvider(LocalSnackBarState provides scaffoldState.snackbarHostState) {
        Scaffold(
            scaffoldState = scaffoldState,
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

}
