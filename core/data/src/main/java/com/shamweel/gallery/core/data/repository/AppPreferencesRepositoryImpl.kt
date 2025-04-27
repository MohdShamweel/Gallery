package com.shamweel.gallery.core.data.repository

import com.shamweel.core.datastore.AppPreferencesDatasource
import com.shamweel.gallery.core.model.AppPrefs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferencesDataSource: AppPreferencesDatasource,
) : AppPreferencesRepository {

    override val appPrefs: Flow<AppPrefs>
        get() = appPreferencesDataSource.prefs

    override suspend fun update(prefs: AppPrefs?) {
        if (prefs == null) return
        appPreferencesDataSource.updatePreferences(prefs)
    }

}