package com.shamweel.gallery.core.domain

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.testing.testing.data.imageMediaSourceData
import com.shamweel.gallery.core.testing.testing.data.videoMediaSourceData
import com.shamweel.gallery.core.testing.testing.repository.TestMediaRepository
import com.shamweel.gallery.core.testing.testing.util.MainDispatcherRule
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class GetMediaAlbumsUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mediaRepository = TestMediaRepository()

    val useCase = GetAlbumsUseCase(mediaRepository)

    @Test
    fun verify_all_image_and_video_media_are_returned() = runTest {
        mediaRepository.setMediaSource(listOf(imageMediaSourceData, videoMediaSourceData))

        val mediaSourceItem = useCase().first()

        TestCase.assertTrue(mediaSourceItem[0].mediaType == MediaType.IMAGE)
        TestCase.assertTrue(mediaSourceItem[1].mediaType == MediaType.VIDEO)
    }

}