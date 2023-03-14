package ru.lyrian.kotlinmultiplatformsandbox.android.feature.videos.list.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.media.YoutubeVideo

private const val CornerShapePercent = 5

@Composable
fun VideosScreen(
    onFullScreenButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    VideosContent(
        modifier = modifier,
        onFullScreenButtonClick = onFullScreenButtonClick
    )
}

@Composable
private fun VideosContent(
    onFullScreenButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val videosIds = listOf(
        "C8JyvzU0CXU",
        "7CZTLogln34",
        "4LMwKwcMdIg",
        "aKWio4zHShM",
        "DJ70N5HahDU",
        "A0FZIwabctw"
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = videosIds.size,
            key = {
                videosIds[it]
            }
        ) {
            VideosListItem(
                videoId = videosIds[it],
                onFullScreenButtonClick = { onFullScreenButtonClick(videosIds[it]) }
            )
        }
    }
}

@Composable
private fun VideosListItem(
    videoId: String,
    onFullScreenButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CornerShapePercent),
        elevation = 8.dp
    ) {
        YoutubeVideo(
            videoId = videoId,
            isFullScreen = false,
            onFullScreenButtonClick = onFullScreenButtonClick
        )
    }
}