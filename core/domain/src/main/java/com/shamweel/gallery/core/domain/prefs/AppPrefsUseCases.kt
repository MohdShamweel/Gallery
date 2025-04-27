package com.shamweel.gallery.core.domain.prefs

data class AppPrefsUseCases(
    val get : GetAppPrefsUseCase,
    val update : UpdateAppPrefUseCase,
)
