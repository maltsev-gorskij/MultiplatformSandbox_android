package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_media.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

private const val CornerShapePercent = 5

@Composable
fun LaunchesMediaScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) {
        LaunchesMediaContent(
            modifier = Modifier
                .padding(it)
        )
    }
}

@Composable
private fun LaunchesMediaContent(
    modifier: Modifier = Modifier
) {
    val videsIds = listOf(
        "C8JyvzU0CXU",
        "7CZTLogln34",
        "4LMwKwcMdIg",
        "aKWio4zHShM",
        "DJ70N5HahDU",
        "A0FZIwabctw"
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = videsIds.size,
            key = {
                videsIds[it]
            }
        ) {
            YoutubeVideo(videoId = videsIds[it])
        }
    }
}

@Composable
fun YoutubeVideo(
    videoId: String
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    Card(
        shape = RoundedCornerShape(CornerShapePercent),
        elevation = 8.dp
    ) {
        AndroidView(
            factory = {
                val view = YouTubePlayerView(it)

                val listener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)

                        view.setCustomPlayerUi(DefaultPlayerUiController(view, youTubePlayer).rootView)
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
}