package com.shamweel.gallery.feature.gallery.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.ui.UriImage
import com.shamweel.ui.VideoPlayer

@Composable
fun MediaCoverGrid(
    modifier: Modifier = Modifier,
    mediaSource: MediaSource?,
    label: String?,
    count: Int? = null,
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
                        .padding(8.dp),
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
                .padding(8.dp),
        ) {
            Text(
                text = label.orEmpty(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge
            )

            count?.let {
                Text(
                    text = count.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }

}