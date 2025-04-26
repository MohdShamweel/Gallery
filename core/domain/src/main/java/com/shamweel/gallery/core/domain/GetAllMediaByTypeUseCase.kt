package com.shamweel.gallery.core.domain

import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.data.repository.MediaRepository
import com.shamweel.gallery.core.model.MediaSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAllMediaByTypeUseCase @Inject constructor(
    private val repository: MediaRepository
) {

    suspend operator fun invoke(type: MediaType): Flow<List<MediaSource>> =
        repository.getAllMediaByType(type)

}