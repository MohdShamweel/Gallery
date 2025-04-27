package com.shamweel.gallery.media

import android.net.Uri
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.orZero
import com.shamweel.gallery.media.demo.DemoMediaDataSource
import com.shamweel.gallery.media.model.MediaSourceFile
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DemoMediaDataSourceTest {

    private lateinit var dataSource: DemoMediaDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        dataSource = DemoMediaDataSource()
    }


    @Test
    fun testEqualityOfDataSource() = runTest(testDispatcher) {
        assertEquals(
            MediaSourceFile(
                id = 1000045274,
                name = "Screenshot_20250426-004757.png,",
                bucketId = 1028075469,
                bucketName = "Screenshots",
                dateAdded = 1745614077,
                author = null,
                size = 64521,
                mediaType = MediaType.IMAGE,
                mimeType = "image/png",
                contentUri = Uri.parse("content://media/external/file/1000045274")
            ),
            dataSource.demoImageMediaSourceFile
        )
    }

    @Test
    fun testValueEqualityOfDataSource() = runTest(testDispatcher) {
        assertEquals(
            1028075469,
            dataSource.demoVideoMediaSourceFile.bucketId.orZero()
        )
    }


}