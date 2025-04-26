package com.shamweel.feature.album.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shamweel.feature.album.AlbumScreen
import com.shamweel.feature.album.AlbumViewModel
import com.shamweel.gallery.core.common.AlbumType
import kotlinx.serialization.Serializable


@Serializable
data class AlbumDataRoute(val bucketId: Long?, val albumType: AlbumType)

fun NavController.navigateToAlbum(
    bucketId: Long?,
    albumType: AlbumType,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = AlbumDataRoute(bucketId, albumType)) {
        navOptions()
    }
}

fun NavGraphBuilder.albumScreen(
    onMediaClick: (Long?, Int, AlbumType) -> Unit,
    onNavigateBack: () -> Unit,
    mediaPagerDestination: NavGraphBuilder.() -> Unit,
) {
    composable<AlbumDataRoute> { entry ->
        val bucketId = entry.toRoute<AlbumDataRoute>().bucketId
        val albumType = entry.toRoute<AlbumDataRoute>().albumType
        AlbumScreen(
            onMediaClick = { bucketId, index ->
                onMediaClick(bucketId, index,  albumType)
            },
            onNavigateBack = onNavigateBack,
            viewModel = hiltViewModel<AlbumViewModel, AlbumViewModel.Factory>(
                key = bucketId.toString(),
            ) { factory ->
                factory.create(bucketId, albumType)
            },
        )
    }
    mediaPagerDestination()
}