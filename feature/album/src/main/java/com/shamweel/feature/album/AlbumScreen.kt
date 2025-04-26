package com.shamweel.feature.album

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Style
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.ui.GradientHeadline
import com.shamweel.ui.MediaGrid
import com.shamweel.ui.MediaLinearStyle
import kotlinx.coroutines.flow.SharedFlow

@Composable
internal fun AlbumScreen(
    modifier: Modifier = Modifier,
    onMediaClick: (Long?, Int) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AlbumScreen(
        modifier = modifier,
        state = state,
        onMediaClick = onMediaClick,
        onNavigateBack = onNavigateBack,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun AlbumScreen(
    modifier: Modifier,
    state: AlbumState,
    onMediaClick: (Long?, Int) -> Unit,
    onNavigateBack: () -> Unit,
    event: SharedFlow<AlbumEvent>,
    onIntent: (AlbumIntent) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier
            .animateContentSize()
            .imePadding()
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
                        text = state.bucketName.orEmpty(),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.statusBarsPadding(),
                        onClick = {
                            onIntent(AlbumIntent.ToggleGridView)
                        }
                    ) {
                        Icon(
                            imageVector = when(state.viewStyle){
                                MediaViewStyle.GRID -> Icons.Default.GridView
                                MediaViewStyle.LINEAR -> Icons.Default.List
                                MediaViewStyle.STAGGERED -> Icons.Default.Style
                            },
                            contentDescription = null
                        )
                    }
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
            when(state.viewStyle) {
                MediaViewStyle.GRID -> {
                    LazyVerticalGrid(
                        state = rememberLazyGridState(),
                        modifier = Modifier
                            .fillMaxSize(),
                        columns = GridCells.Adaptive(100.dp),
                        contentPadding = PaddingValues(0.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(state.mediaList.size) { index ->
                            val album = state.mediaList[index]
                            MediaGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clickable {
                                        onMediaClick(album.bucketId, index)
                                    },
                                mediaSource = album,
                            )
                        }
                    }
                }

                MediaViewStyle.LINEAR -> {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(0.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        items(state.mediaList.size) { index ->
                            val album = state.mediaList[index]
                            MediaLinearStyle(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onMediaClick(album.bucketId, index)
                                    },
                                mediaSource = album
                            )
                        }
                    }
                }

                MediaViewStyle.STAGGERED -> {
                    LazyVerticalStaggeredGrid (
                        state = rememberLazyStaggeredGridState(),
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(0.dp),
                        columns = StaggeredGridCells.Adaptive(100.dp),
                        verticalItemSpacing = 4.dp,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(state.mediaList.size) { index ->
                            val album = state.mediaList[index]
                            MediaGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(if (index %2 == 0) 0.5f else 1f)
                                    .clickable {
                                        onMediaClick(album.bucketId, index)
                                    },
                                mediaSource = album,
                            )
                        }
                    }
                }

            }

        }
    }
}

