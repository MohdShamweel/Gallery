package com.shamweel.gallery.feature.gallery

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.model.AppPrefs
import com.shamweel.gallery.core.model.MediaSource

data class HomeState(
    val albums: MutableMap<String?, List<MediaSource>> = mutableStateMapOf(),
    val mediaAllImages : List<MediaSource> = mutableStateListOf(),
    val mediaAllVideos : List<MediaSource> = mutableStateListOf(),
    val loading : Boolean = true,
    val prefsLoading : Boolean = true,
    val prefs: AppPrefs? = null,
)