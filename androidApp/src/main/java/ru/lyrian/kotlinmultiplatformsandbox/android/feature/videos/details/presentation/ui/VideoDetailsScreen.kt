package ru.lyrian.kotlinmultiplatformsandbox.android.feature.videos.details.presentation.ui

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.MainActivity
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.buttons.ButtonOutlinedSmall
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.media.YoutubeVideo

@Composable
fun VideoDetailsScreen(
    onNavigateBack: () -> Unit,
    videoId: String?,
) {
    val context = LocalContext.current
    val activity = context as MainActivity
    videoId?.let { activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }

    val backHandlerAction = {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        onNavigateBack()
    }

    BackHandler(onBack = backHandlerAction)

    if (videoId == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Something went wrong...")
                ButtonOutlinedSmall(text = "Go back", onClick = backHandlerAction)
            }
        }
    } else {
        VideoDetailsContent(
            onFullScreenButtonClick = backHandlerAction,
            videoId = videoId
        )
    }
}

@Composable
fun VideoDetailsContent(
    onFullScreenButtonClick: () -> Unit,
    videoId: String,
) {
    YoutubeVideo(
        videoId = videoId,
        isFullScreen = true,
        onFullScreenButtonClick = onFullScreenButtonClick
    )
}
