package com.shamweel.feature.mediapager

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.domain.MediaUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MediaPagerViewModel.Factory::class)
class MediaPagerViewModel @AssistedInject constructor(
    private val useCase: MediaUseCases,
    @Assisted val bucketId: Long?,
    @Assisted val albumType: AlbumType,
    @Assisted val index: Int,
) : ViewModel() {

    private val _state = MutableStateFlow(MediaPagerState(selectedIndex = index))
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MediaPagerEvent>()
    val event = _event.asSharedFlow()

    init {
        getAlbum()
    }

    fun getAlbum() = viewModelScope.launch {
        when (albumType) {
            AlbumType.ALL_IMAGES -> {
                useCase.getAllByType(MediaType.IMAGE).collectLatest {
                    onState(
                        state.value.copy(
                            mediaList = it.toMutableStateList(),
                            loading = false
                        )
                    )
                }
            }

            AlbumType.ALL_VIDEOS -> {
                useCase.getAllByType(MediaType.VIDEO).collectLatest {
                    onState(
                        state.value.copy(
                            mediaList = it.toMutableStateList(),
                            loading = false
                        )
                    )
                }
            }

            else -> {
                useCase.getAlbum(bucketId = bucketId ?: 0L).collectLatest {
                    onState(
                        state.value.copy(
                            mediaList = it.toMutableStateList(),
                            loading = false
                        )
                    )
                }
            }
        }

    }


    fun onIntent(intent: MediaPagerIntent) {
        when(intent) {
            is MediaPagerIntent.SelectIndex -> {
                onState(state.value.copy(selectedIndex = intent.index))
            }
        }
    }


    private fun onState(state: MediaPagerState) {
        _state.update { state }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            bucketId: Long?,
            index: Int,
            albumType : AlbumType
        ): MediaPagerViewModel
    }

}