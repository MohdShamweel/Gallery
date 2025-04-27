package com.shamweel.gallery.feature.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.gallery.feature.gallery.component.MediaCoverGrid
import com.shamweel.ui.GradientHeadline
import com.shamweel.ui.StyleLayout
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

    val context = LocalContext.current
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val items = remember { mutableStateListOf<MediaSource>() }
    val label by remember { mutableStateOf<String>("") }

    LaunchedEffect(
        state.mediaAllImages,
        state.mediaAllVideos,
        state.albums
    ) {
        items.clear()
        state.mediaAllImages.firstOrNull()?.let {
            items.add(
                it.copy(
                    bucketName = context.getString(uiR.string.label_all_images),
                    bucketId = null
                )
            )
        }
        state.mediaAllVideos.firstOrNull()?.let {
            items.add(
                it.copy(
                    bucketName = context.getString(uiR.string.label_all_videos),
                    bucketId = null
                )
            )
        }
        val albums = state.albums.entries.map { it.value.first() }
        items.addAll(albums)
    }

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
                actions = {
                    IconButton(
                        modifier = Modifier.statusBarsPadding(),
                        onClick = {
                            onIntent(HomeIntent.ToggleGridView)
                        }
                    ) {
                        Icon(
                            imageVector = when (state.viewStyle) {
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

            @Composable
            fun MediaGridHome(
                modifier: Modifier,
                mediaSource: MediaSource
            ) {
                MediaCoverGrid(
                    modifier = modifier,
                    label = when {
                        mediaSource.bucketId != null -> {
                            mediaSource.bucketName
                        }

                        mediaSource.mediaType == MediaType.IMAGE -> {
                            stringResource(uiR.string.label_all_images)
                        }

                        else -> {
                            stringResource(uiR.string.label_all_videos)
                        }
                    },
                    mediaSource = mediaSource,
                    count = when {
                        mediaSource.bucketId != null -> {
                            state.albums[mediaSource.bucketName]?.size
                        }

                        mediaSource.mediaType == MediaType.IMAGE -> {
                            state.mediaAllImages.size
                        }

                        else -> {
                            state.mediaAllVideos.size
                        }
                    }
                )

            }

            StyleLayout(
                modifier = Modifier
                    .fillMaxSize(),
                viewStyle = state.viewStyle,
                adaptiveWidth = 150.dp,
                space = 8.dp,
                list = items,
                onItemClick = {
                    when {
                        it.bucketId != null -> {
                            onAlbumClick(AlbumType.BUCKET, it.bucketId)
                        }

                        it.mediaType == MediaType.IMAGE -> {
                            onAlbumClick(AlbumType.ALL_IMAGES, null)
                        }

                        it.mediaType == MediaType.VIDEO -> {
                            onAlbumClick(AlbumType.ALL_VIDEOS, null)
                        }
                    }
                },
                onGridItemContent = {
                    MediaGridHome(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        mediaSource = it,
                    )
                },
                onColumnItemContent = {
                    MediaGridHome(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        mediaSource = it
                    )
                },
                onStaggeredGridItemContent = {
                    MediaGridHome(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(if (it.id?.rem(2L) == 0L) 0.5f else 1f),
                        mediaSource = it
                    )
                }

            )
        }
    }
}