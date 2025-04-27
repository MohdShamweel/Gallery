package com.shamweel.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class GradientHeadlineTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val testLabel = "Gallery"

    @Test
    fun testGradientHeadline_withLabel() {
        composeTestRule.setContent {
            GradientHeadline(
                text = testLabel,
            )
        }

        composeTestRule
            .onNodeWithText(testLabel)
            .assertIsDisplayed()
    }
}