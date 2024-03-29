package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.media

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import ru.lyrian.kotlinmultiplatformsandbox.core.common.logger.SharedLogger

/**
 * Image with progress bar based on Coil AsyncImage.
 * */
@Composable
fun ImageFromUrl(
    model: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    onImageLoading: () -> Unit = {},
    onImageLoadSuccess: () -> Unit = {},
    onImageLoadError: () -> Unit = {},
    contentDescription: String? = null,
    error: Painter? = null,
) {
    var isLoading by remember { mutableStateOf(false) }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
            error = error,
            onLoading = {
                isLoading = true
                onImageLoading()
            },
            onSuccess = {
                isLoading = false
                onImageLoadSuccess()
            },
            onError = {
                isLoading = false
                onImageLoadError()
                SharedLogger.logError(
                    message = "Cannot load image: $model",
                    throwable = null,
                    tag = this.javaClass.simpleName
                )
            },
        )
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
