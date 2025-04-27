package com.shamweel.gallery.feature.gallery

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.common.ShareInstance
import com.shamweel.gallery.core.domain.MediaUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: MediaUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        MediaType.entries.forEach { getMedia(it) }
    }

    fun getMedia(type: MediaType) {
        when (type) {
            MediaType.IMAGE -> {
                viewModelScope.launch {
                    useCase.getAllByType(type).collectLatest {
                        onState(
                            state.value.copy(
                                mediaAllImages = it.toMutableStateList(),
                                loading = false
                            )
                        )
                    }
                }
            }

            MediaType.VIDEO -> {
                viewModelScope.launch {
                    useCase.getAllByType(type).collectLatest {
                        onState(
                            state.value.copy(
                                mediaAllVideos = it.toMutableStateList(),
                                loading = false
                            )
                        )
                    }
                }
            }

            MediaType.FILE -> {
                viewModelScope.launch {
                    useCase.getAlbums().collectLatest {
                        val albums = it.groupBy { it.bucketName }
                        onState(
                            state.value.copy(
                                albums = albums.toMutableMap(),
                                loading = false
                            )
                        )
                    }
                }
            }
        }
    }


    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.ToggleGridView -> {
                val mediaStyle = when (state.value.viewStyle) {
                    MediaViewStyle.GRID -> MediaViewStyle.LINEAR
                    MediaViewStyle.LINEAR -> MediaViewStyle.STAGGERED
                    MediaViewStyle.STAGGERED -> MediaViewStyle.GRID
                }
                ShareInstance.mediaViewStyle = mediaStyle
                onState(
                    state.value.copy(
                        viewStyle = mediaStyle
                    )
                )
            }
        }

    }


    private fun onState(state: HomeState) {
        _state.update { state }
    }

}