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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shamweel.gallery.core.common.utils.ConversionUtil
import com.shamweel.gallery.core.common.utils.DateUtils
import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.ui.MediaGrid
import java.util.concurrent.TimeUnit

@Composable
fun MediaLinearStyle(
    modifier: Modifier = Modifier,
    mediaSource: MediaSource?,
) {

    var date = remember(mediaSource?.dateAdded) {
        DateUtils.getDateForPattern(
            (mediaSource?.dateAdded ?: 0L).times(TimeUnit.SECONDS.toMillis(1))
        )
    }

    val size = remember(mediaSource?.size) {
        ConversionUtil.bytesToHumanReadableSize(mediaSource?.size?.toDouble() ?: 0.0)
    }

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

            date?.let {
                Text(
                    text = date,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = size.toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }

}