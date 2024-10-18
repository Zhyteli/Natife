package com.test.natife.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.natife.domain.GiphyRepository
import com.test.natife.domain.models.GifObject
import com.test.natife.domain.usecase.GetSearchGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val searchGifsUseCase: GetSearchGifsUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("funny cats")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _gifs = _query
        .flatMapLatest { query ->
            searchGifsUseCase(query)
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val gifs: StateFlow<PagingData<GifObject>> = _gifs

    fun setQuery(query: String) {
        _query.value = query
    }
}

