package com.test.natife.data.models.base

data class GiphyResponseData(
    val data: List<GifObjectEntity>,
    val paginationData: PaginationData
)