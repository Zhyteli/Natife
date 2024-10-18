package com.test.natife.domain.usecase

import com.test.natife.domain.GiphyRepository
import com.test.natife.domain.models.GifObject
import javax.inject.Inject

class SaveGifUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(gif: GifObject) {
        repository.saveGif(gif)
    }
}
