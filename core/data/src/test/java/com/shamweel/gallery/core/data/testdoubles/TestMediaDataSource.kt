package com.shamweel.gallery.core.data.testdoubles

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.media.MediaDataSource
import com.shamweel.gallery.media.demo.DemoMediaDataSource
import com.shamweel.gallery.media.model.MediaSourceFile
import kotlinx.coroutines.runBlocking

class TestMediaDataSource : MediaDataSource {

    private val source = DemoMediaDataSource()

    private val allImages = runBlocking { source.getAllMediaByType(MediaType.IMAGE) }
    private val allVideos = runBlocking { source.getAllMediaByType(MediaType.VIDEO) }
    private val allAlbums = runBlocking { source.getAlbums() }

    override suspend fun getAllMediaByType(type: MediaType): List<MediaSourceFile> {
       return when(type) {
           MediaType.IMAGE -> allImages
           MediaType.VIDEO -> allVideos
           else -> allImages
       }
    }

    override suspend fun getAlbums(): List<MediaSourceFile> {
        return allAlbums
    }

    override suspend fun getAlbum(bucketId: Long): List<MediaSourceFile> {
        return allAlbums.filter { it.bucketId == bucketId }
    }

}