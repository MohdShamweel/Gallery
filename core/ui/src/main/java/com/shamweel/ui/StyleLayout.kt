package com.shamweel.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.model.MediaSource

@Composable
fun StyleLayout(
    modifier: Modifier = Modifier,
    viewStyle: MediaViewStyle,
    adaptiveWidth : Dp = 100.dp,
    space: Dp = 2.dp,
    list: List<MediaSource>,
    onItemClick: (MediaSource) -> Unit,
    onGridItemContent: @Composable LazyGridItemScope.(mediaSource: MediaSource) -> Unit,
    onStaggeredGridItemContent: @Composable LazyStaggeredGridItemScope.(mediaSource: MediaSource) -> Unit,
    onColumnItemContent: @Composable LazyItemScope.(mediaSource: MediaSource) -> Unit,
) {

    when (viewStyle) {
        MediaViewStyle.GRID -> {
            LazyVerticalGrid(
                state = rememberLazyGridState(),
                modifier = modifier,
                columns = GridCells.Adaptive(adaptiveWidth),
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(space),
                horizontalArrangement = Arrangement.spacedBy(space)
            ) {
                items(list.size) { index ->
                    val mediaSource = list[index]
                    Box(
                        modifier = Modifier
                            .clickable { onItemClick(mediaSource) }) {
                        onGridItemContent(mediaSource)
                    }
                }
            }

        }

        MediaViewStyle.LINEAR -> {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = modifier,
                contentPadding = PaddingValues(0.dp),
                verticalArrangement = Arrangement.spacedBy(space),
            ) {
                items(list.size) { index ->
                    val mediaSource = list[index]
                    Box(
                        modifier = Modifier
                            .clickable { onItemClick(mediaSource) }) {
                        onColumnItemContent(mediaSource)
                    }
                }
            }
        }

        MediaViewStyle.STAGGERED -> {
            LazyVerticalStaggeredGrid(
                state = rememberLazyStaggeredGridState(),
                modifier = modifier,
                contentPadding = PaddingValues(0.dp),
                columns = StaggeredGridCells.Adaptive(adaptiveWidth),
                verticalItemSpacing = space,
                horizontalArrangement = Arrangement.spacedBy(space)
            ) {
                items(list.size) { index ->
                    val mediaSource = list[index]
                    Box(
                        modifier = Modifier
                            .clickable { onItemClick(mediaSource) }) {
                        onStaggeredGridItemContent(mediaSource)
                    }
                }
            }
        }
    }

}