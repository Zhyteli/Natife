package com.test.natife.data.models.net

data class GifObjectNet(
    val id: String,
    val imagesNet: ImagesNet
)

data class ImagesNet(
    val originalImageNet: OriginalImageNet
)

data class OriginalImageNet(
    val url: String
)