package com.shamweel.feature.mediapager

sealed interface MediaPagerIntent {
    data class SelectIndex(val index: Int) : MediaPagerIntent
}