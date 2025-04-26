package com.shamweel.gallery.core.domain.di

import com.shamweel.gallery.core.domain.GetAlbumUseCase
import com.shamweel.gallery.core.domain.GetAlbumsUseCase
import com.shamweel.gallery.core.domain.GetAllMediaByTypeUseCase
import com.shamweel.gallery.core.domain.GetMediaUseCase
import com.shamweel.gallery.core.domain.MediaUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun providesMediaUseCase(
        getAllByType: GetAllMediaByTypeUseCase,
        getAlbums: GetAlbumsUseCase,
        getAlbum: GetAlbumUseCase,
        getMedia: GetMediaUseCase,
    ): MediaUseCases = MediaUseCases(
        getAllByType = getAllByType,
        getAlbums = getAlbums,
        getAlbum = getAlbum,
        getMedia = getMedia,
    )

}