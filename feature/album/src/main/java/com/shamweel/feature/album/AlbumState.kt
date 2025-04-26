package com.shamweel.feature.album

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.model.MediaSource

data class AlbumState(
    val mediaList: List<MediaSource> = mutableStateListOf(),
    val bucketId: Long? = null,
    val bucketName: String? = null,
    val albumType : AlbumType? = null,
    val loading: Boolean = true,
    val isGridView : Boolean = true
)