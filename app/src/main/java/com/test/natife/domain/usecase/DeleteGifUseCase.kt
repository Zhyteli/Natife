package com.test.natife.domain.usecase

import com.test.natife.domain.GiphyRepository
import javax.inject.Inject

class DeleteGifUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(gifId: String) {
        repository.deleteGif(gifId)
    }
}