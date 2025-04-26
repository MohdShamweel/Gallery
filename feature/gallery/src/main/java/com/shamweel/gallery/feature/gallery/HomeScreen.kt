package com.shamweel.gallery.feature.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shamweel.ui.AlbumImage
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        state = state,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    state: HomeState,
    event: SharedFlow<HomeEvent>,
    onIntent: (HomeIntent) -> Unit
) {
    Box(modifier) {
        LazyVerticalGrid(
            state = rememberLazyGridState(),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.mediaAllImages.isNotEmpty()){
                item {
                    AlbumImage(
                        uri = state.mediaAllImages[0].contentUri,
                        label = "All Images",
                        count = state.mediaAllImages.size
                    )
                }
            }

            if (state.mediaAllVideos.isNotEmpty()){
                item {
                    AlbumImage(
                        uri = state.mediaAllVideos[0].contentUri,
                        label = "All Videos",
                        count = state.mediaAllVideos.size
                    )
                }
            }

            items(state.albums.size) {
                val album = state.albums.entries.elementAtOrNull(it)
                AlbumImage(
                    uri = album?.value[0]?.contentUri,
                    label = album?.key,
                    count = album?.value?.size
                )
            }
        }
    }

}