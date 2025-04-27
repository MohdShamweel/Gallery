package com.shamweel.core.datastore

import androidx.datastore.core.DataStore
import com.core.datastore.AppPreferences
import com.core.datastore.copy
import com.shamweel.gallery.core.model.AppPrefs
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppPreferencesDatasource @Inject constructor(
    private val appPreferences: DataStore<AppPreferences>
) {

    val prefs = appPreferences.data
        .map {
            AppPrefs(
                mediaViewStyleCode = it.mediaViewStyleCode
            )
        }

    suspend fun updatePreferences(appPrefs: AppPrefs?) = appPrefs?.let {
        try {
            appPreferences.updateData {
                it.copy {
                    this.mediaViewStyleCode = appPrefs.mediaViewStyleCode
                }
            }

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }


}
