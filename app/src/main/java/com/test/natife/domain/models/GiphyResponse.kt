package com.test.natife.domain.models

data class GiphyResponse(
    val data: List<GifObject>,
    val pagination: Pagination
)