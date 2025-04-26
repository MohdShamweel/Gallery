package com.shamweel.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.shamweel.gallery.core.designsystem.theme.Neutral100

@Composable
fun GradientHeadline(
    modifier: Modifier = Modifier,
    text: String,
) {

    val brush = Brush.linearGradient(
        listOf(
            Neutral100.copy(alpha = 0.9f),
            Neutral100.copy(alpha = 0.6f),
            Neutral100.copy(alpha = 0.5f),
            Neutral100.copy(alpha = 0.3f),
            Neutral100.copy(alpha = 0.2f),
            Neutral100.copy(alpha = 0.1f)
        )
    )

    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier,
            text = text,
            color = Neutral100.copy(alpha = 0.4f),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall.copy(
                brush = brush
            )
        )

    }
}


@Preview
@Composable
private fun Preview() {
    GradientHeadline(text = "All Images")
}
