package com.shamweel.feature.mediapager.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.ui.MediaGrid

@Composable
fun HorizontalMediaRow(
    modifier: Modifier = Modifier,
    list: List<MediaSource>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedIndex) {
        if (selectedIndex > 0 && selectedIndex < list.size) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(list.size) { index ->
            MediaGrid(
                modifier = Modifier
                    .size(60.dp)
                    .aspectRatio(1f)
                    .alpha(if (selectedIndex == index) 1f else 0.6f)
                    .clickable {
                        onSelected(index)
                    },
                mediaSource = list[index],
            )
        }
    }

}