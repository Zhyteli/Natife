package com.test.natife.data.models.base

data class GifObjectData(
    val id: String,
    val imagesData: ImagesData
)

data class ImagesData(
    val originalImageData: OriginalImageData
)

data class OriginalImageData(
    val url: String,
    val height: String,
    val width: String
)