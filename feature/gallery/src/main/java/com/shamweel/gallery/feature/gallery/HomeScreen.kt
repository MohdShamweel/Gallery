package com.shamweel.gallery.feature.gallery

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.ui.GradientHeadline
import com.shamweel.ui.MediaCover
import kotlinx.coroutines.flow.SharedFlow
import com.shamweel.gallery.core.ui.R as uiR

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onAlbumClick: (AlbumType, Long?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        state = state,
        onAlbumClick = onAlbumClick,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier,
    state: HomeState,
    onAlbumClick: (AlbumType, Long?) -> Unit,
    event: SharedFlow<HomeEvent>,
    onIntent: (HomeIntent) -> Unit
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Transparent,
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    GradientHeadline(
                        modifier = Modifier,
                        text = "Gallery",
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {

            LazyVerticalGrid(
                state = rememberLazyGridState(),
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.mediaAllImages.isNotEmpty()) {
                    item {
                        MediaCover(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable {
                                    onAlbumClick(AlbumType.ALL_IMAGES, null)
                                },
                            label = stringResource(uiR.string.label_all_images),
                            mediaSource = state.mediaAllImages.firstOrNull(),
                            count = state.mediaAllImages.size
                        )
                    }
                }

                if (state.mediaAllVideos.isNotEmpty()) {
                    item {
                        MediaCover(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clickable {
                                    onAlbumClick(AlbumType.ALL_VIDEOS, null)
                                },
                            label = stringResource(uiR.string.label_all_videos),
                            mediaSource = state.mediaAllVideos.firstOrNull(),
                            count = state.mediaAllVideos.size
                        )
                    }
                }

                items(state.albums.size) {
                    val album = state.albums.entries.elementAtOrNull(it)
                    MediaCover(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable {
                                onAlbumClick(
                                    AlbumType.BUCKET,
                                    album?.value?.firstOrNull()?.bucketId
                                )
                            },
                        label = album?.key,
                        mediaSource = album?.value?.firstOrNull(),
                        count = album?.value?.size
                    )
                }
            }
        }

    }
}