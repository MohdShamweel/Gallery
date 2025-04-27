package com.shamweel.gallery.core.data.repository

import com.shamweel.gallery.core.model.AppPrefs
import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    val appPrefs : Flow<AppPrefs>

    suspend fun update(prefs: AppPrefs?)

}