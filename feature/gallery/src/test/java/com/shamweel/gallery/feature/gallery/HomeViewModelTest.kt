package com.shamweel.gallery.feature.gallery

import com.shamweel.gallery.core.common.MediaViewStyle
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
import com.shamweel.gallery.core.testing.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

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
    fun setup() {
        viewModel = HomeViewModel(mediaUseCases, appPrefsUseCases)
    }

    @Test
    fun state_whenInitialized_thenShowLoading() = runTest {
        assertTrue(viewModel.state.value.loading)
    }

    @Test
    fun state_whenLayoutView_isChanged() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.initData.collect() }
        mediaRepository.setMediaSource(listOf(imageMediaSourceData, videoMediaSourceData))
        appPrefsRepository.update(emptyAppPrefs)
        viewModel.onIntent(HomeIntent.ToggleGridView)
        assertEquals(
            MediaViewStyle.LINEAR.code,
            viewModel.state.value.prefs?.mediaViewStyleCode
        )
        collectJob.cancel()
    }

}