package ru.lyrian.kotlinmultiplatformsandbox.android.feature.videos.details.presentation.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.MainActivity
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.media.YoutubeVideo

@Composable
fun VideoDetailsScreen(
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context as MainActivity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    val backHandlerAction = {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        onNavigateBack()
    }

    BackHandler(onBack = backHandlerAction)

    VideoDetailsContent(onFullScreenButtonClick = backHandlerAction)
}

@Composable
fun VideoDetailsContent(
    onFullScreenButtonClick: () -> Unit,
) {
    YoutubeVideo(
        videoId = "4LMwKwcMdIg",
        isFullScreen = true,
        onFullScreenButtonClick = onFullScreenButtonClick
    )
}