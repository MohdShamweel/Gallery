package com.shamweel.gallery.core.testing.testing.repository

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.data.repository.MediaRepository
import com.shamweel.gallery.core.model.MediaSource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestMediaRepository : MediaRepository {

    private val mediaSourceFlow: MutableSharedFlow<List<MediaSource>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun setMediaSource(mediaSources: List<MediaSource>) {
        mediaSourceFlow.tryEmit(mediaSources)
    }

    override suspend fun getAllMediaByType(type: MediaType): Flow<List<MediaSource>> {
        return mediaSourceFlow
    }

    override suspend fun getAlbums(): Flow<List<MediaSource>> {
        return mediaSourceFlow
    }

    override suspend fun getAlbum(bucketId: Long): Flow<List<MediaSource>> {
        return mediaSourceFlow
    }

}