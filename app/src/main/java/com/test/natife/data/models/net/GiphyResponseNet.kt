package com.test.natife.data.models.net

import com.test.natife.domain.models.GifObject

data class GiphyResponseNet(
    val data: List<GifObject>,
    val paginationNet: PaginationNet
)