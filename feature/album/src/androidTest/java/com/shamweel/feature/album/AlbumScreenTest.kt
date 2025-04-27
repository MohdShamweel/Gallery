package com.shamweel.feature.album

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
class AlbumScreenTest {

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

    private lateinit var viewModel: AlbumViewModel

    @Before
    fun setup(){
        viewModel = AlbumViewModel(
            mediaUseCases,
            appPrefsUseCases,
            null,
            AlbumType.ALL_IMAGES
        )
        parentBoxTestTag = "test:album:parentBox"
        circularLoadingTestTag = "test:album:loader"
        styleLayoutTestTag = "test:album:styleLayout"
    }

    @Test
    fun circularLoading_isShown_onLoading() {
        composeTestRule.setContent {
            AlbumScreen(
                viewModel = viewModel,
                onMediaClick = { _, _ -> },
                onNavigateBack = {}
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
            AlbumScreen(
                viewModel = viewModel,
                onMediaClick = { _, _ -> },
                onNavigateBack = {}
            )

            DisposableEffect(Unit) {
                val collectJob = scope.launch(UnconfinedTestDispatcher()) { viewModel.initData.collect() }
                mediaRepository.setMediaSource(listOf(imageMediaSourceData))
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
            AlbumScreen(
                viewModel = viewModel,
                onMediaClick = { _, _ -> },
                onNavigateBack = {}
            )

            DisposableEffect(Unit) {
                val collectJob = scope.launch(UnconfinedTestDispatcher()) { viewModel.initData.collect() }
                mediaRepository.setMediaSource(listOf(imageMediaSourceData))
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
    }
}