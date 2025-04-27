package com.shamweel.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GradientHeadline(
    modifier: Modifier = Modifier,
    text: String,
    color : Color = MaterialTheme.colorScheme.onBackground
) {

    val brush = Brush.linearGradient(
        listOf(
            color.copy(alpha = 0.9f),
            color.copy(alpha = 0.6f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.2f),
            color.copy(alpha = 0.1f)
        )
    )

    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier,
            text = text,
            color = color.copy(alpha = 0.4f),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium.copy(
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
