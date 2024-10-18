package com.test.natife.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.test.natife.data.models.net.isInternetAvailable
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

/**
 * GiphyViewModel is a ViewModel class designed to manage the UI-related data for displaying GIFs
 * and handling user interactions such as searching, deleting individual GIFs, or clearing all GIFs.
 * This ViewModel is annotated with `@HiltViewModel` to support Dependency Injection using Hilt.
 * It extends `AndroidViewModel` to access the application context for checking internet connectivity.
 *
 * Key Responsibilities:
 * - Manages GIF search queries and handles the business logic for fetching and displaying GIFs.
 * - Uses coroutines and flows to efficiently handle data loading and caching.
 * - Provides functions for deleting individual GIFs and clearing all cached GIFs.
 *
 * Key Properties and Functions:
 * - `_query`: A `MutableStateFlow` that holds the current search query for fetching GIFs. It is
 *   initialized with "funny cats" as the default search term.
 *
 * - `_gifs`: A flow that reacts to changes in the search query, triggers the deletion of all GIFs
 *   when there is an internet connection, and fetches GIFs using the `GetSearchGifsUseCase`. It maps
 *   the domain objects to UI objects and caches the result within the `viewModelScope`.
 *
 * - `gifs`: A public `StateFlow` that exposes the paginated data (PagingData<GifObjectUi>) to the UI.
 *
 * - `setQuery(query: String)`: Allows the user to set a new search query, triggering a search for GIFs
 *   and clearing all cached GIFs.
 *
 * - `deleteGif(gifId: String)`: Deletes a specific GIF from the database, identified by its `gifId`.
 *
 * @param searchGifsUseCase - Use case for fetching paginated GIF data based on a search query.
 * @param deleteGifUseCase - Use case for deleting a specific GIF.
 * @param deleteAllGifUseCase - Use case for clearing all GIF data from the database.
 * @param application - The application context, used to check network connectivity.
 */

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val searchGifsUseCase: GetSearchGifsUseCase,
    private val deleteGifUseCase: DeleteGifUseCase,
    private val deleteAllGifUseCase: DeleteAllGifUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _query = MutableStateFlow("funny cats")

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _gifs = _query
        .flatMapLatest { query ->
            if (isInternetAvailable(application)) deleteAllGifUseCase()
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

