package com.shamweel.gallery.core.domain

import com.shamweel.gallery.core.data.repository.MediaRepository
import com.shamweel.gallery.core.model.MediaSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val repository: MediaRepository
) {

    suspend operator fun invoke(): Flow<List<MediaSource>> =
        repository.getAlbums()

}