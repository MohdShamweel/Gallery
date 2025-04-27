package com.shamweel.feature.mediapager

import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.domain.GetAlbumUseCase
import com.shamweel.gallery.core.domain.GetAlbumsUseCase
import com.shamweel.gallery.core.domain.GetAllMediaByTypeUseCase
import com.shamweel.gallery.core.domain.MediaUseCases
import com.shamweel.gallery.core.testing.testing.data.imageMediaSourceData
import com.shamweel.gallery.core.testing.testing.data.videoMediaSourceData
import com.shamweel.gallery.core.testing.testing.repository.TestMediaRepository
import com.shamweel.gallery.core.testing.testing.util.MainDispatcherRule
import junit.framework.TestCase
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
class MediaPagerViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mediaRepository = TestMediaRepository()

    private val mediaUseCases = MediaUseCases(
        getAllByType = GetAllMediaByTypeUseCase(mediaRepository),
        getAlbums = GetAlbumsUseCase(mediaRepository),
        getAlbum = GetAlbumUseCase(mediaRepository)
    )

    private lateinit var viewModel: MediaPagerViewModel

    @Before
    fun setup() {
        viewModel = MediaPagerViewModel(
            mediaUseCases,
            null,
            AlbumType.ALL_IMAGES,
            0
        )
    }

    @Test
    fun state_whenInitialized_thenShowLoading() = runTest {
        assertTrue(viewModel.state.value.loading)
    }

    @Test
    fun state_whenLSelectedIndex_isChanged() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.initData.collect() }
        mediaRepository.setMediaSource(listOf(imageMediaSourceData, videoMediaSourceData))
        viewModel.onIntent(MediaPagerIntent.SelectIndex(1))
        TestCase.assertEquals(
            1,
            viewModel.state.value.selectedIndex
        )
        collectJob.cancel()
    }

}