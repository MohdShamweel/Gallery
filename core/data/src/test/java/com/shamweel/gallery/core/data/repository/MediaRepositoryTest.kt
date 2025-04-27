package com.shamweel.gallery.core.data.repository

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.data.mapper.toExternalMediaSource
import com.shamweel.gallery.core.data.testdoubles.TestMediaDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MediaRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject : MediaRepositoryImpl

    private lateinit var mediaDataSource: TestMediaDataSource

    @Before
    fun setup() {
        mediaDataSource = TestMediaDataSource()
        subject = MediaRepositoryImpl(mediaDataSource)
    }

    @Test
    fun mediaRepository_getAllMediaByType_verifyMediaList() =
        testScope.runTest {
            assertEquals(
                mediaDataSource.getAllMediaByType(MediaType.IMAGE).first().toExternalMediaSource(),
                subject.getAllMediaByType(MediaType.IMAGE).first().first()
            )
        }
}