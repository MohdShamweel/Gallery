package com.shamweel.gallery.core.data.di

import com.shamweel.gallery.core.data.repository.MediaRepository
import com.shamweel.gallery.core.data.repository.MediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule  {

    @Binds
    fun bindsMediaRepository(
        mediaRepositoryImpl: MediaRepositoryImpl
    ) : MediaRepository

}