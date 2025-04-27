package com.shamweel.gallery.feature.gallery

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.domain.MediaUseCases
import com.shamweel.gallery.core.domain.prefs.AppPrefsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: MediaUseCases,
    private val prefsUseCases: AppPrefsUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    val initData: StateFlow<HomeState?> = combine(
        flow { emit(useCase.getAllByType(MediaType.IMAGE)) } ,
        flow { emit(useCase.getAllByType(MediaType.VIDEO)) },
        flow { emit( useCase.getAlbums()) },
        prefsUseCases.get()
    ) { images, videos, albums, prefs ->
        HomeState(
            mediaAllImages = images.first().toMutableStateList(),
            mediaAllVideos = videos.first().toMutableStateList(),
            albums = albums.first().groupBy { it.bucketName }.toMutableMap(),
            prefs = prefs,
            loading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeState()
    )

    init {
        getInitData()
    }

    private fun getInitData() = viewModelScope.launch {
        initData.collectLatest {
            if (it != null) {
                onState(it)
            }
        }
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.ToggleGridView -> {
                val prefStyle =
                    MediaViewStyle.entries.find { it.code == state.value.prefs?.mediaViewStyleCode }
                val mediaStyle = when (prefStyle) {
                    MediaViewStyle.GRID -> MediaViewStyle.LINEAR
                    MediaViewStyle.LINEAR -> MediaViewStyle.STAGGERED
                    else -> MediaViewStyle.GRID
                }
                viewModelScope.launch {
                    val prefs = state.value.prefs?.copy(
                        mediaViewStyleCode = mediaStyle.code
                    )
                    prefsUseCases.update(prefs)
                }
            }
        }

    }


    private fun onState(state: HomeState) {
        _state.update { state }
    }

}