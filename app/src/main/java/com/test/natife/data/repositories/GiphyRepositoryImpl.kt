package com.test.natife.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.test.natife.data.database.GifDao
import com.test.natife.data.mappers.toDomain
import com.test.natife.presentation.mappers.toDomain
import com.test.natife.data.mappers.toEntity
import com.test.natife.data.network.GiphyApiService
import com.test.natife.data.paging.GiphyRemoteMediator
import com.test.natife.di.ApiKey
import com.test.natife.domain.GiphyRepository
import com.test.natife.domain.models.GifObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * GiphyRepositoryImpl is the implementation of the GiphyRepository interface, providing
 * methods to interact with the Giphy API and the local database (GifDao) for fetching, saving,
 * and deleting GIFs. This class uses dependency injection with the `@Inject` annotation to
 * provide the required dependencies (API service, DAO, and API key).
 *
 * This repository handles paging with the Paging 3 library, providing a stream of GIFs through
 * a Flow that fetches data from both the remote Giphy API and the local database cache.
 *
 * Key Functions:
 * - getGifsStream(query: String): Returns a Flow of PagingData containing GifObject, which
 *   represents the result of a paginated GIF search based on a query. It uses the GiphyRemoteMediator
 *   for deciding whether to load data from the network or the database.
 *
 * - saveGif(gif: GifObject): Saves a single GifObject to the local database by converting it to its
 *   entity form and storing it via the GifDao.
 *
 * - deleteGif(gifId: String): Marks a GIF as deleted in the local database using its unique ID.
 *
 * - deleteAllGif(): Clears all GIF data from the local database.
 *
 * Parameters:
 * @param apiService - The Giphy API service for making network requests.
 * @param gifDao - The DAO for accessing and modifying GIFs in the local database.
 * @param apiKey - The API key for authenticating requests to the Giphy API.
 */

@Singleton
class GiphyRepositoryImpl @Inject constructor(
    private val apiService: GiphyApiService,
    private val gifDao: GifDao,
    @ApiKey private val apiKey: String
): GiphyRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getGifsStream(query: String): Flow<PagingData<GifObject>> {
        val pagingSourceFactory = { gifDao.getPagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 25,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = GiphyRemoteMediator(apiService, gifDao, apiKey, query),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData ->
                pagingData.map { entity ->
                    entity.toDomain()
                }
            }
    }


    override suspend fun saveGif(gif: GifObject) {
        gifDao.insertGif(gif.toEntity())
    }

    override suspend fun deleteGif(gifId: String) {
        gifDao.markGifAsDeleted(gifId)
    }

    override suspend fun deleteAllGif() {
        gifDao.clearAllGifs()
    }
}


