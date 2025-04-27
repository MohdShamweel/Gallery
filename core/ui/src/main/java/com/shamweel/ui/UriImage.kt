package com.shamweel.ui


import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun UriImage(
    modifier: Modifier = Modifier,
    uri: Uri?,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        modifier = modifier,
        model = uri,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}