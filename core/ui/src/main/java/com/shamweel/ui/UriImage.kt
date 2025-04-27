package com.shamweel.ui


import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun UriImage(
    modifier: Modifier = Modifier,
    uri: Uri?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    enableShimmer: Boolean = true
) {

    var showShimmer by remember { mutableStateOf(false) }
    AsyncImage(
        modifier = modifier,
        model = uri,
        contentDescription = contentDescription,
        contentScale = contentScale,
        onLoading = {
            showShimmer = true
        },
        onSuccess = {
            showShimmer = false
        },
        onError = {
            showShimmer = false
        }
    )
    if (showShimmer && enableShimmer) {
        Box(
            modifier.shimmerEffect()
        )
    }

}