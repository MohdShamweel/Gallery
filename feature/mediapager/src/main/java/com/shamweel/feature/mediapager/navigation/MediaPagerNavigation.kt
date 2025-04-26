package com.shamweel.feature.mediapager.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shamweel.feature.mediapager.MediaPagerScreen
import com.shamweel.feature.mediapager.MediaPagerViewModel
import com.shamweel.gallery.core.common.AlbumType
import kotlinx.serialization.Serializable


@Serializable
data class MediaPagerRoute(val bucketId: Long?, val index: Int, val albumType: AlbumType)

fun NavController.navigateToMediaPager(
    bucketId: Long?,
    index: Int,
    albumType: AlbumType,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = MediaPagerRoute(bucketId, index, albumType)) {
        navOptions()
    }
}

fun NavGraphBuilder.mediaPagerScreen(
    onNavigateBack: () -> Unit
) {
    composable<MediaPagerRoute> { entry ->
        val bucketId = entry.toRoute<MediaPagerRoute>().bucketId
        val albumType = entry.toRoute<MediaPagerRoute>().albumType
        val index = entry.toRoute<MediaPagerRoute>().index
        MediaPagerScreen(
            onNavigateBack = onNavigateBack,
            viewModel = hiltViewModel<MediaPagerViewModel, MediaPagerViewModel.Factory>(
                key = bucketId.toString(),
            ) { factory ->
                factory.create(bucketId, index, albumType)
            }
        )
    }
}