package com.shamweel.feature.mediapager

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shamweel.feature.mediapager.component.HorizontalMediaRow
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.secondsToFormatterDate
import com.shamweel.gallery.core.common.toReadableSize
import com.shamweel.ui.MediaPage
import kotlinx.coroutines.flow.SharedFlow
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun MediaPagerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: MediaPagerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MediaPagerScreen(
        modifier = modifier,
        state = state,
        onNavigateBack = onNavigateBack,
        event = viewModel.event,
        onIntent = viewModel::onIntent
    )

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun MediaPagerScreen(
    modifier: Modifier,
    state: MediaPagerState,
    onNavigateBack: () -> Unit,
    event: SharedFlow<MediaPagerEvent>,
    onIntent: (MediaPagerIntent) -> Unit
) {

    val pagerState = rememberPagerState(state.selectedIndex) { state.mediaList.size }
    val pageMedia =
        remember(pagerState.currentPage) { state.mediaList.getOrNull(pagerState.currentPage) }

    val horizontalScrollBottomPadding by animateDpAsState(
        targetValue = if (pageMedia?.mediaType == MediaType.VIDEO) 100.dp else 32.dp,
        animationSpec = tween(1000, easing = LinearEasing)
    )

    LaunchedEffect(state.selectedIndex) {
        pagerState.animateScrollToPage(state.selectedIndex)
    }

    Scaffold(
        modifier = modifier
            .animateContentSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    val title = pageMedia?.name
                    title?.let {
                        Text(
                            modifier = Modifier,
                            text = StringBuilder(title)
                                .append("\n")
                                .append(pageMedia.dateAdded.secondsToFormatterDate()).append(" | ")
                                .append(pageMedia.size.toReadableSize())
                                .toString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("test:mediaPager:parentBox")
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("test:mediaPager:pager"),
                contentPadding = PaddingValues(0.dp),
                pageSpacing = 16.dp
            ) { index ->
                val album = state.mediaList[index]
                MediaPage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    mediaSource = album,
                )
            }

            HorizontalMediaRow(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = horizontalScrollBottomPadding)
                    .testTag("test:mediaPager:row"),
                list = state.mediaList,
                selectedIndex = pagerState.currentPage,
                onSelected = {
                    onIntent(MediaPagerIntent.SelectIndex(it))
                }
            )
        }

    }
}

