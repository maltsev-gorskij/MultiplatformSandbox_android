package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph.VideoDetailsGraph
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.videos.details.presentation.ui.VideoDetailsScreen

fun NavGraphBuilder.videoDetailsNavGraph(
    onNavigateBack: () -> Unit
) {
    navigation(
        route = VideoDetailsGraph.GRAPH_ROUTE,
        startDestination = VideoDetailsGraph.DETAILS
    ) {
        composable("${VideoDetailsGraph.DETAILS}/{${VideoDetailsGraph.DetailsArgs.VIDEO_ID}}") {
            VideoDetailsScreen(
                onNavigateBack = onNavigateBack,
                videoId = it.arguments?.getString(VideoDetailsGraph.DetailsArgs.VIDEO_ID)
            )
        }
    }
}