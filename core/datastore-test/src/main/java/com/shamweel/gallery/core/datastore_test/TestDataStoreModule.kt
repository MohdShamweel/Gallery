package com.shamweel.gallery.core.datastore_test

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.core.datastore.AppPreferences
import com.shamweel.core.datastore.AppPreferencesSerializer
import com.shamweel.core.datastore.di.DataStoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.rules.TemporaryFolder
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class],
)
internal object TestDataStoreModule {

    @Provides
    @Singleton
    fun providesAppPreferencesDataStore(
        appPreferencesSerializer: AppPreferencesSerializer,
        tmpFolder: TemporaryFolder,
    ): DataStore<AppPreferences> =
        tmpFolder.tesAppPreferencesDataStore(
            appPreferencesSerializer = appPreferencesSerializer,
        )
}

fun TemporaryFolder.tesAppPreferencesDataStore(
    appPreferencesSerializer: AppPreferencesSerializer = AppPreferencesSerializer(),
) = DataStoreFactory.create(
    serializer = appPreferencesSerializer,
    scope = CoroutineScope(Dispatchers.IO),
) {
    newFile("app_prefs_test.pb")
}
