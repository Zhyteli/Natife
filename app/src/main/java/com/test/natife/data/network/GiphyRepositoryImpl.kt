package com.test.natife.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.natife.di.ApiKey
import com.test.natife.domain.GiphyRepository
import com.test.natife.domain.models.GifObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepositoryImpl @Inject constructor(
    private val apiService: GiphyApiService,
    @ApiKey private val apiKey: String
): GiphyRepository {
    override fun getGifsStream(query: String): Flow<PagingData<GifObject>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                initialLoadSize = 25, // Размер первоначальной загрузки
                prefetchDistance = 3,  // Расстояние до конца списка, при котором начинается подгрузка
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GiphyPagingSource(apiService, apiKey, query) }
        ).flow
    }
}


