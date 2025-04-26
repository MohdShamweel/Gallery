package com.shamweel.feature.album

sealed interface AlbumIntent {
    object ToggleGridView : AlbumIntent
}