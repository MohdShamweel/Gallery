package com.shamweel.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.model.MediaSource

@Composable
fun MediaGrid(
    modifier: Modifier = Modifier,
    mediaSource: MediaSource?,
) {
    if (mediaSource?.contentUri == null) return

    Box(modifier) {
        when (mediaSource.mediaType) {
            MediaType.VIDEO -> {
                VideoPlayer(
                    modifier = Modifier
                        .fillMaxSize(),
                    uri = mediaSource.contentUri ?: return,
                    isAdjustSelfAspectRatio = false,
                    isAutoPlay = false,
                    isScaleCrop = true,
                    isUseController = false,
                )

                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(16.dp),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            else -> {
                UriImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    uri = mediaSource.contentUri,
                    contentDescription = mediaSource.name,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

}