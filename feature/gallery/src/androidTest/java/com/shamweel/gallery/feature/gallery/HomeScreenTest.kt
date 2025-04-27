package com.shamweel.gallery.feature.gallery

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
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
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private lateinit var parentBoxTestTag : String
    private lateinit var circularLoadingTestTag : String
    private lateinit var styleLayoutTestTag : String

    private val appPrefsRepository = TestAppPreferenceRepository()
    private val getAppPrefsUseCase = GetAppPrefsUseCase(appPrefsRepository)
    private val saveAppPrefsUseCase = UpdateAppPrefUseCase(appPrefsRepository)

    private val appPrefsUseCases = AppPrefsUseCases(
        get = getAppPrefsUseCase,
        update = saveAppPrefsUseCase
    )

    private val mediaRepository = TestMediaRepository()

    private val mediaUseCases = MediaUseCases(
        getAllByType = GetAllMediaByTypeUseCase(mediaRepository),
        getAlbums = GetAlbumsUseCase(mediaRepository),
        getAlbum = GetAlbumUseCase(mediaRepository)
    )

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup(){
        viewModel = HomeViewModel(mediaUseCases, appPrefsUseCases)
        parentBoxTestTag = "test:home:parentBox"
        circularLoadingTestTag = "test:home:loader"
        styleLayoutTestTag = "test:home:styleLayout"
    }

    @Test
    fun circularLoading_isShown_onLoading() {
        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                onAlbumClick = { _, _ -> },
            )
        }

        composeTestRule
            .onNodeWithTag(circularLoadingTestTag)
            .assertExists()
    }

    @Test
    fun circularLoading_isHides_onSuccess() {
        composeTestRule.setContent {
            val scope = rememberCoroutineScope()
            HomeScreen(
                viewModel = viewModel,
                onAlbumClick = { _, _ -> },
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
            .onNodeWithTag(circularLoadingTestTag)
            .assertDoesNotExist()
    }

    @Test
    fun check_if_media_are_populated_style_layout() {
        composeTestRule.setContent {
            val scope = rememberCoroutineScope()
            HomeScreen(
                viewModel = viewModel,
                onAlbumClick = { _, _ -> },
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
            .onNodeWithTag(styleLayoutTestTag)
            .assertExists()
            .performScrollToIndex(1)
    }
}