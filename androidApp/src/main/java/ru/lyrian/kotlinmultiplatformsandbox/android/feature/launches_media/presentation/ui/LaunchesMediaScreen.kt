package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_media.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.media.YoutubeVideo

private const val CornerShapePercent = 5

@Composable
fun LaunchesMediaScreen(
    onFullScreenButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier) {
        LaunchesMediaContent(
            modifier = Modifier.padding(it),
            onFullScreenButtonClick = onFullScreenButtonClick
        )
    }
}

@Composable
private fun LaunchesMediaContent(
    onFullScreenButtonClick: (String) -> Unit,
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
            YoutubeVideo(
                videoId = videsIds[it],
                shape = RoundedCornerShape(CornerShapePercent),
                elevation = 8.dp,
                onFullScreenButtonClick = { onFullScreenButtonClick(videsIds[it]) }
            )
        }
    }
}