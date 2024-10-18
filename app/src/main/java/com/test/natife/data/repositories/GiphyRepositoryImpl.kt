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


