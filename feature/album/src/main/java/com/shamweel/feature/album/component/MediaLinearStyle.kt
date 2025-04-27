package com.shamweel.feature.album.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shamweel.gallery.core.common.secondsToFormatterDate
import com.shamweel.gallery.core.common.toReadableSize
import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.ui.MediaGrid

@Composable
fun MediaLinearStyle(
    modifier: Modifier = Modifier,
    mediaSource: MediaSource?,
) {

    Row(
        modifier = modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        MediaGrid(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(60.dp)
                .aspectRatio(1f),
            mediaSource = mediaSource,
        )

        Column {
            Text(
                text = mediaSource?.name.orEmpty(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground
            )

            mediaSource?.dateAdded?.let {
                Text(
                    text = it.secondsToFormatterDate().orEmpty(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = mediaSource?.size.toReadableSize(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }

}