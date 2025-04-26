package com.shamweel.feature.mediapager

import androidx.compose.runtime.mutableStateListOf
import com.shamweel.gallery.core.model.MediaSource

data class MediaPagerState(
    val mediaList: List<MediaSource> = mutableStateListOf(),
    val loading: Boolean = true,
    val selectedIndex : Int = 0
)