package com.shamweel.feature.album

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel(assistedFactory = AlbumViewModel.Factory::class)
class AlbumViewModel @AssistedInject constructor(
    private val useCase: MediaUseCases,
    @Assisted val bucketId: Long?,
    @Assisted val albumType: AlbumType,
) : ViewModel() {

    private val _state = MutableStateFlow(AlbumState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AlbumEvent>()
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
                            bucketId = bucketId,
                            bucketName = albumType.label,
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
                            bucketId = bucketId,
                            bucketName = albumType.label,
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
                            bucketId = bucketId,
                            bucketName = it.firstOrNull()?.bucketName,
                            loading = false
                        )
                    )
                }
            }
        }

    }


    fun onIntent(intent: AlbumIntent) {
        when (intent) {
            AlbumIntent.ToggleGridView -> {
                onState(
                    state.value.copy(
                        isGridView = !state.value.isGridView
                    )
                )
            }
        }

    }


    private fun onState(state: AlbumState) {
        _state.update { state }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            bucketId: Long?,
            albumType: AlbumType
        ): AlbumViewModel
    }

}