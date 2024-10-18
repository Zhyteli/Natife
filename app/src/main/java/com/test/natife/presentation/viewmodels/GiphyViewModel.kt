package com.test.natife.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.test.natife.domain.GiphyRepository
import com.test.natife.domain.models.GifObject
import com.test.natife.domain.usecase.DeleteGifUseCase
import com.test.natife.domain.usecase.GetSearchGifsUseCase
import com.test.natife.domain.usecase.SaveGifUseCase
import com.test.natife.presentation.mappers.toDomain
import com.test.natife.presentation.mappers.toUi
import com.test.natife.presentation.models.GifObjectUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val searchGifsUseCase: GetSearchGifsUseCase,
    private val saveGifUseCase: SaveGifUseCase,
    private val deleteGifUseCase: DeleteGifUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("funny cats")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _gifs = _query
        .flatMapLatest { query ->
            searchGifsUseCase(query)
        }
        .map { pagingData ->
            pagingData.map { gifObject ->
                gifObject.toUi()
            }
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val gifs: StateFlow<PagingData<GifObjectUi>> = _gifs


    fun setQuery(query: String) {
        _query.value = query
    }

    fun saveGif(gif: GifObjectUi) {
        viewModelScope.launch {
            saveGifUseCase(gif.toDomain())
        }
    }

    fun deleteGif(gifId: String) {
        viewModelScope.launch {
            deleteGifUseCase(gifId)
//            _query.value = _query.value
        }
    }
}

