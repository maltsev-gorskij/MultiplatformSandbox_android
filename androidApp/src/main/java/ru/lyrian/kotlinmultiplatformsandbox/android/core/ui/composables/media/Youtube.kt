package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.media

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

@Composable
fun YoutubeVideo(
    videoId: String,
    onFullScreenButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = false
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(
        modifier = modifier,
        factory = {
            val view = YouTubePlayerView(it)

            val listener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)

                    val uiController = DefaultPlayerUiController(view, youTubePlayer)
                    uiController.setFullScreenButtonClickListener {
                        onFullScreenButtonClick()
                    }
                    if (isFullScreen) view.enterFullScreen()
                    view.setCustomPlayerUi(uiController.rootView)
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }

            val iFramePlayerOptions = IFramePlayerOptions
                .Builder()
                .controls(0)
                .build()

            lifecycle.addObserver(view)
            view.enableAutomaticInitialization = false
            view.initialize(listener, iFramePlayerOptions)
            view
        }
    )
}