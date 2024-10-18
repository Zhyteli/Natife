package com.test.natife.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.test.natife.data.database.GifDao
import com.test.natife.data.mappers.toEntity
import com.test.natife.data.models.base.GifObjectEntity
import com.test.natife.data.network.GiphyApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * GiphyRemoteMediator is a RemoteMediator implementation that handles fetching GIFs
 * from the Giphy API and caching them into a local database using Paging 3 library.
 *
 * This mediator is responsible for deciding when to load data (e.g., refresh, append, prepend),
 * handling pagination logic, and interacting with both the network layer (Giphy API)
 * and the local database (GifDao).
 *
 * It uses the experimental Paging 3 API's `ExperimentalPagingApi` annotation. The mediator fetches
 * GIFs based on a search query and stores the results into a Room database for future paging.
 *
 * Parameters:
 * @param apiService - The service interface for making API calls to Giphy.
 * @param gifDao - The Data Access Object (DAO) for interacting with the local database.
 * @param apiKey - The API key used to authenticate requests to the Giphy API.
 * @param query - The search query to fetch GIFs matching the provided keyword(s).
 *
 * The load function determines the load type (REFRESH, APPEND, PREPEND) and fetches data accordingly.
 * If data is successfully fetched, it inserts the GIFs into the local database. In case of errors,
 * such as network issues (IOException) or API errors (HttpException), appropriate error handling is done.
 */

@OptIn(ExperimentalPagingApi::class)
class GiphyRemoteMediator(
    private val apiService: GiphyApiService,
    private val gifDao: GifDao,
    private val apiKey: String,
    private val query: String
) : RemoteMediator<Int, GifObjectEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifObjectEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    0
                } else {
                    state.pages.size * state.config.pageSize
                }
            }
        }

        try {
            val response = apiService.searchGifs(
                apiKey = apiKey,
                query = query,
                limit = state.config.pageSize,
                offset = page
            )

            val gifs = response.data.map { it.toEntity() }

            gifDao.insertGifs(gifs) // Use the new function

            return MediatorResult.Success(endOfPaginationReached = gifs.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}
