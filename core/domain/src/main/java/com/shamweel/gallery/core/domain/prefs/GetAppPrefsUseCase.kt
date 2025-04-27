package com.shamweel.gallery.core.domain.prefs

import com.shamweel.gallery.core.data.repository.AppPreferencesRepository
import javax.inject.Inject

class GetAppPrefsUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    operator fun invoke() = appPreferencesRepository.appPrefs
}