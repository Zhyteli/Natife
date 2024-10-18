package com.test.natife.domain.usecase

import androidx.paging.PagingData
import com.test.natife.domain.models.GifObject
import com.test.natife.domain.GiphyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchGifsUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    operator fun invoke(query: String): Flow<PagingData<GifObject>> {
        return repository.getGifsStream(query)
    }
}
