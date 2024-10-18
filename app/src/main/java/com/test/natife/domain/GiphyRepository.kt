package com.test.natife.domain

import androidx.paging.PagingData
import com.test.natife.domain.models.GifObject
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    fun getGifsStream(query: String): Flow<PagingData<GifObject>>
    suspend fun saveGif(gif: GifObject)
    suspend fun deleteGif(gifId: String)
}