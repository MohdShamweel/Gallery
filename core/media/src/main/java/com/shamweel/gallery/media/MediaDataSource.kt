package com.shamweel.gallery.media

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.media.model.MediaSourceFile

interface MediaDataSource {

    suspend fun getAllMediaByType(type: MediaType): List<MediaSourceFile>

    suspend fun getAlbums(): List<MediaSourceFile>

    suspend fun getAlbum(bucketId: Long): List<MediaSourceFile>

}