package com.shamweel.gallery.core.data.repository

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.data.mapper.toExternalMediaSource
import com.shamweel.gallery.core.model.MediaSource
import com.shamweel.gallery.media.MediaDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaDataSource: MediaDataSource
) : MediaRepository {

    override suspend fun getAllMediaByType(type: MediaType): Flow<List<MediaSource>> = flow {
        emit(mediaDataSource.getAllMediaByType(type).map { it.toExternalMediaSource() })
    }

    override suspend fun getAlbums(): Flow<List<MediaSource>> = flow {
        emit(mediaDataSource.getAlbums().map { it.toExternalMediaSource() })
    }

    override suspend fun getAlbum(bucketId: Long): Flow<List<MediaSource>> = flow {
        emit(mediaDataSource.getAlbum(bucketId).map { it.toExternalMediaSource() })
    }

    override suspend fun getMedia(mediaId: Long): Flow<List<MediaSource>> = flow {
        emit(mediaDataSource.getMedia(mediaId).map { it.toExternalMediaSource() })
    }


}