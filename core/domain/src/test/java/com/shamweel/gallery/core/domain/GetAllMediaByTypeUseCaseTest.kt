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

class GetAllMediaByTypeUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mediaRepository = TestMediaRepository()

    val useCase = GetAllMediaByTypeUseCase(mediaRepository)

    @Test
    fun verify_all_images_are_returned() = runTest {
        mediaRepository.setMediaSource(listOf(imageMediaSourceData))

        val mediaSourceItem = useCase(MediaType.IMAGE).first()

        mediaSourceItem.forEach {
            TestCase.assertTrue(it.mediaType == MediaType.IMAGE)
            TestCase.assertTrue(it.bucketName?.isNotEmpty() == true)
        }
    }

    @Test
    fun verify_all_videos_are_returned() = runTest {
        mediaRepository.setMediaSource(listOf(videoMediaSourceData))

        val mediaSourceItem = useCase(MediaType.VIDEO).first()

        mediaSourceItem.forEach {
            TestCase.assertTrue(it.mediaType == MediaType.VIDEO)
            TestCase.assertTrue(it.bucketName?.isNotEmpty() == true)
        }
    }


}