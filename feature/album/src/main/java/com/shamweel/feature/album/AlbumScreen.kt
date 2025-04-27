package com.shamweel.feature.album

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shamweel.feature.album.component.MediaLinearStyle
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.ui.GradientHeadline
import com.shamweel.ui.MediaGrid
import com.shamweel.ui.StyleLayout
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
    val mediaViewStyle = remember(state.prefs?.mediaViewStyleCode) {
        MediaViewStyle.entries.firstOrNull { it.code == state.prefs?.mediaViewStyleCode }
            ?: MediaViewStyle.GRID
    }

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
                    Text(
                        text = state.mediaList.size.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                    IconButton(
                        modifier = Modifier.statusBarsPadding(),
                        onClick = {
                            onIntent(AlbumIntent.ToggleGridView)
                        }
                    ) {
                        Icon(
                            imageVector = when (mediaViewStyle) {
                                MediaViewStyle.GRID -> Icons.Default.GridView
                                MediaViewStyle.LINEAR -> Icons.Default.List
                                else -> Icons.Default.Style
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

            when {
                state.prefsLoading || state.loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(32.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                else -> {

                    StyleLayout(
                        modifier = Modifier
                            .fillMaxSize(),
                        viewStyle = mediaViewStyle,
                        list = state.mediaList,
                        onItemClick = {
                            onMediaClick(it.bucketId, state.mediaList.indexOf(it))
                        },
                        onGridItemContent = {
                            MediaGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                mediaSource = it,
                            )
                        },
                        onColumnItemContent = {
                            MediaLinearStyle(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                mediaSource = it
                            )
                        },
                        onStaggeredGridItemContent = {
                            MediaGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(if (it.id?.rem(2L) == 0L) 0.5f else 1f),
                                mediaSource = it,
                            )
                        }
                    )
                }
            }
        }
    }
}
