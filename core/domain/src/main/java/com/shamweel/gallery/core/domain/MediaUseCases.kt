package com.shamweel.gallery.core.domain

data class MediaUseCases(
    val getAllByType: GetAllMediaByTypeUseCase,
    val getAlbums: GetAlbumsUseCase,
    val getAlbum: GetAlbumUseCase,
    val getMedia: GetMediaUseCase,
)