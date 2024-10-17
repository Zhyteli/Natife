package com.test.natife

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.natife.domain.models.GifObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private val _query = MutableStateFlow("funny cats")
    val gifs: Flow<PagingData<GifObject>> = _query.flatMapLatest { query ->
        repository.getGifsStream(query).cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        _query.value = query
    }
}

