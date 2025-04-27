package com.shamweel.gallery.core.domain.prefs

import com.shamweel.gallery.core.data.repository.AppPreferencesRepository
import com.shamweel.gallery.core.model.AppPrefs
import javax.inject.Inject


class UpdateAppPrefUseCase @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {

    suspend operator fun invoke(
        appPrefs: AppPrefs?
    ) = appPreferencesRepository.update(appPrefs)
}