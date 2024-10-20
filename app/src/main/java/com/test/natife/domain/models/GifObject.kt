package com.test.natife.domain.models

data class GifObject(
    val id: String,
    val images: Images,
    val isDeleted: Boolean = false
)

data class Images(
    val original: OriginalImage
)

data class OriginalImage(
    val url: String,
    val height: String,
    val width: String
)