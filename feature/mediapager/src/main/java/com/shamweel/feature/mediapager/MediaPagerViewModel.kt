package com.shamweel.feature.mediapager

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.orZero
import com.shamweel.gallery.core.domain.MediaUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
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

    val initData: StateFlow<MediaPagerState> =
        flow {
            val media = when (albumType) {
                AlbumType.ALL_IMAGES -> useCase.getAllByType(MediaType.IMAGE)
                AlbumType.ALL_VIDEOS -> useCase.getAllByType(MediaType.VIDEO)
                AlbumType.BUCKET -> useCase.getAlbum(bucketId.orZero())
            }
            emit(
                MediaPagerState(
                    selectedIndex = index,
                    mediaList = media.first().toMutableStateList(),
                    loading = false
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MediaPagerState()
        )

    init {
        getInitData()
    }

    fun getInitData() {
        viewModelScope.launch {
            initData.collectLatest {
                onState(it)
            }
        }
    }

    fun onIntent(intent: MediaPagerIntent) {
        when (intent) {
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
            albumType: AlbumType
        ): MediaPagerViewModel
    }

}