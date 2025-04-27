package com.shamweel.gallery

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppTest  {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var scaffoldTestTag : String

    @Before
    fun setup(){
        scaffoldTestTag = "test:scaffold:app"
        hiltRule.inject()
    }

    @Test
    fun check_if_scaffold_displayed() {
        composeTestRule
            .onNodeWithTag(scaffoldTestTag)
            .assertExists()
    }
}