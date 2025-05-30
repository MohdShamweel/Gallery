package com.shamweel.gallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shamweel.feature.album.navigation.albumScreen
import com.shamweel.feature.album.navigation.navigateToAlbum
import com.shamweel.feature.mediapager.navigation.mediaPagerScreen
import com.shamweel.feature.mediapager.navigation.navigateToMediaPager
import com.shamweel.gallery.feature.gallery.navigation.HomeBaseRoute
import com.shamweel.gallery.feature.gallery.navigation.homeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = HomeBaseRoute
    ) {
        homeScreen(
            onAlbumClick = { albumType, bucketId ->
                navController.navigateToAlbum(bucketId, albumType)
            },
            albumDestination = {
                albumScreen(
                    onMediaClick = { bucketId, index , albumType ->
                         navController.navigateToMediaPager(bucketId, index,  albumType)
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    mediaPagerDestination = {
                        mediaPagerScreen {
                            navController.popBackStack()
                        }
                    }
                )
            }
        )
    }

}