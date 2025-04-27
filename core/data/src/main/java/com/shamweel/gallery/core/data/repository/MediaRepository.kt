package com.shamweel.gallery.core.data.repository

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.model.MediaSource
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    suspend fun getAllMediaByType(type: MediaType): Flow<List<MediaSource>>

    suspend fun getAlbums(): Flow<List<MediaSource>>

    suspend fun getAlbum(bucketId: Long): Flow<List<MediaSource>>

}