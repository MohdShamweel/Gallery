package com.shamweel.gallery.media.di

import com.shamweel.gallery.media.MediaDataSource
import com.shamweel.gallery.media.source.MediaDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MediaSourceModule  {

    @Binds
    fun bindsMediaSource(
        mediaDataSourceImpl: MediaDataSourceImpl
    ) : MediaDataSource

}