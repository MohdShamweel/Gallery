package com.shamweel.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.core.datastore.AppPreferences
import com.shamweel.core.datastore.AppPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesAppPreferencesDataStore(
        @ApplicationContext context: Context,
        appPreferencesSerializer: AppPreferencesSerializer
    ): DataStore<AppPreferences> =
        DataStoreFactory.create(
            serializer = appPreferencesSerializer,
            scope = CoroutineScope(Dispatchers.IO),
            migrations = listOf()
        ) {
            context.dataStoreFile("app_prefs.pb")
        }

}