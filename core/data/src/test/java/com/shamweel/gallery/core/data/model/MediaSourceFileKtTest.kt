package com.shamweel.gallery.core.data.model

import android.net.Uri
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.data.mapper.toExternalMediaSource
import com.shamweel.gallery.media.model.MediaSourceFile
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MediaSourceFileKtTest {

    @Test
    fun mediaSourceFile_toExternalMediaSource_returns_mediaSource() {
        val mediaSourceFile = MediaSourceFile(
            id = 1000045274,
            name = "Screenshot_20250426-004757.png,",
            bucketId = 1028075469,
            bucketName = "Screenshots",
            dateAdded = 1745614077,
            author = null,
            size = 64521,
            mediaType = MediaType.IMAGE,
            mimeType = "mage/png",
            contentUri = Uri.parse("content://media/external/file/1000045274")
        )

        val externalModel = mediaSourceFile.toExternalMediaSource()

        assertEquals(mediaSourceFile.id, externalModel.id)
        assertEquals(mediaSourceFile.name, externalModel.name)
        assertEquals(mediaSourceFile.bucketId, externalModel.bucketId)
        assertEquals(mediaSourceFile.bucketName, externalModel.bucketName)
        assertEquals(mediaSourceFile.dateAdded, externalModel.dateAdded)
        assertEquals(mediaSourceFile.author, externalModel.author)
        assertEquals(mediaSourceFile.size, externalModel.size)
        assertEquals(mediaSourceFile.mediaType, externalModel.mediaType)
        assertEquals(mediaSourceFile.mimeType, externalModel.mimeType)
        assertEquals(mediaSourceFile.contentUri, externalModel.contentUri)
    }
}