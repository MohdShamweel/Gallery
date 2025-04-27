package com.shamweel.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.model.MediaSource

@Composable
fun MediaPage(
    modifier: Modifier = Modifier,
    mediaSource: MediaSource?,
) {
    if (mediaSource?.contentUri == null) return


    Box(
        modifier = modifier.fillMaxSize()
    )
    {
        when (mediaSource.mediaType) {
            MediaType.VIDEO -> {
                VideoPlayer(
                    modifier = Modifier
                        .fillMaxSize(),
                    uri = mediaSource.contentUri,
                    isAdjustSelfAspectRatio = true,
                    isAutoPlay = true,
                    isScaleCrop = false,
                    isUseController = true,
                    isMute = false
                )
            }

            else -> {
                UriImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    uri = mediaSource.contentUri,
                    contentDescription = mediaSource.name,
                    contentScale = ContentScale.FillWidth,
                    enableShimmer = false
                )
            }
        }
    }
}