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
