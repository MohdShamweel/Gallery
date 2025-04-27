package com.shamweel.gallery.core.testing.testing.repository

import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.common.orZero
import com.shamweel.gallery.core.data.repository.AppPreferencesRepository
import com.shamweel.gallery.core.model.AppPrefs
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

val emptyAppPrefs = AppPrefs(
    mediaViewStyleCode = MediaViewStyle.GRID.code,
)

class TestAppPreferenceRepository : AppPreferencesRepository {

    private val _appPrefs =
        MutableSharedFlow<AppPrefs>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private val currentAppPrefs get() = _appPrefs.replayCache.firstOrNull() ?: emptyAppPrefs

    override val appPrefs: Flow<AppPrefs> = _appPrefs.filterNotNull()

    override suspend fun update(prefs: AppPrefs?) {
        currentAppPrefs.let { current ->
            _appPrefs.tryEmit(
                current.copy(
                    mediaViewStyleCode = prefs?.mediaViewStyleCode.orZero()
                )
            )
        }
    }

}