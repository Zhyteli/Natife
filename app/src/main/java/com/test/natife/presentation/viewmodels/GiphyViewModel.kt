package com.test.natife.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.test.natife.domain.usecase.DeleteAllGifUseCase
import com.test.natife.domain.usecase.DeleteGifUseCase
import com.test.natife.domain.usecase.GetSearchGifsUseCase
import com.test.natife.presentation.mappers.toUi
import com.test.natife.presentation.models.GifObjectUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val searchGifsUseCase: GetSearchGifsUseCase,
    private val deleteGifUseCase: DeleteGifUseCase,
    private val deleteAllGifUseCase: DeleteAllGifUseCase,
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
        viewModelScope.launch {
            deleteAllGifUseCase()
        }
        _query.value = query
    }

    fun deleteGif(gifId: String) {
        viewModelScope.launch {
            deleteGifUseCase(gifId)
        }
    }
}

