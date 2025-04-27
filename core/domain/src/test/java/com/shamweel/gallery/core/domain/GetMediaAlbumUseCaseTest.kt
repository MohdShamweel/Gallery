package com.shamweel.gallery.core.domain

import com.shamweel.gallery.core.common.orZero
import com.shamweel.gallery.core.testing.testing.data.imageMediaSourceData
import com.shamweel.gallery.core.testing.testing.repository.TestMediaRepository
import com.shamweel.gallery.core.testing.testing.util.MainDispatcherRule
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class GetMediaAlbumUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mediaRepository = TestMediaRepository()

    val useCase = GetAlbumUseCase(mediaRepository)

    @Test
    fun verify_bucket_of_album_is_returned() = runTest {
        mediaRepository.setMediaSource(listOf(imageMediaSourceData))

        val mediaSources = useCase(imageMediaSourceData.bucketId.orZero()).first()

        mediaSources.forEach {
            TestCase.assertTrue(it.bucketId == imageMediaSourceData.bucketId)
        }
    }

}