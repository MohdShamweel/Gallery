package com.shamweel.feature.album

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamweel.gallery.core.common.AlbumType
import com.shamweel.gallery.core.common.MediaType
import com.shamweel.gallery.core.common.MediaViewStyle
import com.shamweel.gallery.core.common.orZero
import com.shamweel.gallery.core.domain.MediaUseCases
import com.shamweel.gallery.core.domain.prefs.AppPrefsUseCases
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = AlbumViewModel.Factory::class)
class AlbumViewModel @AssistedInject constructor(
    private val useCase: MediaUseCases,
    private val prefsUseCases: AppPrefsUseCases,
    @Assisted val bucketId: Long?,
    @Assisted val albumType: AlbumType,
) : ViewModel() {

    private val _state = MutableStateFlow(AlbumState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AlbumEvent>()
    val event = _event.asSharedFlow()

    val initData: StateFlow<AlbumState?> = combine(
        flow {
            emit(
                when (albumType) {
                    AlbumType.ALL_IMAGES -> useCase.getAllByType(MediaType.IMAGE)
                    AlbumType.ALL_VIDEOS -> useCase.getAllByType(MediaType.VIDEO)
                    AlbumType.BUCKET -> useCase.getAlbum(bucketId.orZero())
                }
            )
        },
        prefsUseCases.get()
    ) { listFlow, prefs ->
        val list = listFlow.first().toMutableStateList()
        AlbumState(
            mediaList = list,
            bucketName = when (albumType) {
                AlbumType.BUCKET -> list.firstOrNull()?.bucketName
                else -> albumType.label
            },
            bucketId = bucketId,
            prefs = prefs,
            loading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AlbumState()
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

    fun onIntent(intent: AlbumIntent) {
        when (intent) {
            AlbumIntent.ToggleGridView -> {
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