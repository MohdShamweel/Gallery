package com.shamweel.gallery.feature.gallery

sealed interface HomeIntent {
    object ToggleGridView : HomeIntent
}