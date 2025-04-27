package com.shamweel.feature.mediapager

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.domain.GetAlbumUseCase
import com.shamweel.gallery.core.domain.GetAlbumsUseCase
import com.shamweel.gallery.core.domain.GetAllMediaByTypeUseCase
import com.shamweel.gallery.core.domain.MediaUseCases
import com.shamweel.gallery.core.domain.prefs.AppPrefsUseCases
import com.shamweel.gallery.core.domain.prefs.GetAppPrefsUseCase
import com.shamweel.gallery.core.domain.prefs.UpdateAppPrefUseCase
import com.shamweel.gallery.core.testing.testing.data.imageMediaSourceData
import com.shamweel.gallery.core.testing.testing.data.videoMediaSourceData
import com.shamweel.gallery.core.testing.testing.repository.TestAppPreferenceRepository
import com.shamweel.gallery.core.testing.testing.repository.TestMediaRepository
import com.shamweel.gallery.core.testing.testing.repository.emptyAppPrefs
import com.shamweel.gallery.ui_test_hilt_manifest.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class MediaPagerScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private lateinit var parentBoxTestTag : String
    private lateinit var horizontalPagerTestTag : String
    private lateinit var horizontalRowTestTag : String

    private val mediaRepository = TestMediaRepository()

    private val mediaUseCases = MediaUseCases(
        getAllByType = GetAllMediaByTypeUseCase(mediaRepository),
        getAlbums = GetAlbumsUseCase(mediaRepository),
        getAlbum = GetAlbumUseCase(mediaRepository)
    )

    private lateinit var viewModel: MediaPagerViewModel

    @Before
    fun setup(){
        viewModel = MediaPagerViewModel(
            mediaUseCases,
            null,
            AlbumType.ALL_IMAGES,
            0
        )
        parentBoxTestTag = "test:mediaPager:parentBox"
        horizontalPagerTestTag = "test:mediaPager:pager"
        horizontalRowTestTag = "test:mediaPager:row"
    }

    @Test
    fun check_if_parentBox_is_shown() {
        composeTestRule.setContent {
            MediaPagerScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )
        }

        composeTestRule
            .onNodeWithTag(parentBoxTestTag)
            .assertExists()
    }

    @Test
    fun check_if_horizontalPager_is_shown_after_data_loads() {
        composeTestRule.setContent {
            val scope = rememberCoroutineScope()
            MediaPagerScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )

            DisposableEffect(Unit) {
                val collectJob = scope.launch(UnconfinedTestDispatcher()) { viewModel.initData.collect() }
                mediaRepository.setMediaSource(listOf(imageMediaSourceData, videoMediaSourceData))
                val prefsJob = scope.launch {
                    appPrefsRepository.update(emptyAppPrefs)
                }

                onDispose {
                    collectJob.cancel()
                    prefsJob.cancel()
                }
            }

        }

        composeTestRule
            .onNodeWithTag(horizontalPagerTestTag)
            .assertExists()
    }

    @Test
    fun check_if_horizontalRow_at_bottom_is_shown() {
        composeTestRule.setContent {
            val scope = rememberCoroutineScope()
            MediaPagerScreen(
                viewModel = viewModel,
                onNavigateBack = {}
            )

            DisposableEffect(Unit) {
                val collectJob = scope.launch(UnconfinedTestDispatcher()) { viewModel.initData.collect() }
                mediaRepository.setMediaSource(listOf(imageMediaSourceData, videoMediaSourceData))
                val prefsJob = scope.launch {
                    appPrefsRepository.update(emptyAppPrefs)
                }

                onDispose {
                    collectJob.cancel()
                    prefsJob.cancel()
                }
            }

        }

        composeTestRule
            .onNodeWithTag(horizontalRowTestTag)
            .assertExists()
            .performScrollToIndex(1)
    }
}