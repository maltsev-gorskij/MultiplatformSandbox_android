package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
val LocalScaffoldPaddings = compositionLocalOf<PaddingValues> { error("No PaddingValues provided") }

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        CompositionLocalProvider(
            LocalSnackBarState provides scaffoldState.snackbarHostState,
            LocalScaffoldPaddings provides it
        ) {
            HomeNavGraph(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
