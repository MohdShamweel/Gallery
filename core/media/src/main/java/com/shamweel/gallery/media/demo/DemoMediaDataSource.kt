package com.shamweel.gallery.media.demo

import android.net.Uri
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.media.MediaDataSource
import com.shamweel.gallery.media.model.MediaSourceFile
import androidx.core.net.toUri

class DemoMediaDataSource  : MediaDataSource {

    override suspend fun getAllMediaByType(type: MediaType): List<MediaSourceFile> {
       return listOf(
           when(type) {
               MediaType.IMAGE -> demoImageMediaSourceFile
               MediaType.VIDEO -> demoVideoMediaSourceFile
               else -> demoImageMediaSourceFile
           }
       )
    }

    override suspend fun getAlbums(): List<MediaSourceFile> {
        return listOf(demoImageMediaSourceFile, demoVideoMediaSourceFile)
    }

    override suspend fun getAlbum(bucketId: Long): List<MediaSourceFile> {
        return listOf(
            demoImageMediaSourceFile.copy(bucketId = bucketId),
            demoVideoMediaSourceFile.copy(bucketId = bucketId)
        )
    }

    val demoImageMediaSourceFile = MediaSourceFile(
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
    )

    val demoVideoMediaSourceFile = MediaSourceFile(
        id = 1000045274,
        name = "Video_20250426-004757.mp4,",
        bucketId = 1028075469,
        bucketName = "Screenshots",
        dateAdded = 1745614077,
        author = null,
        size = 645212,
        mediaType = MediaType.VIDEO,
        mimeType = "video/mp4",
        contentUri = Uri.parse("content://media/external/file/1000045274")
    )
}