package com.test.natife.data.models.net

data class GifObjectNet(
    val id: String,
    val images: ImagesNet,
    val isDeleted: Boolean = false
)

data class ImagesNet(
    val original: OriginalImageNet
)

data class OriginalImageNet(
    val url: String,
    val height: String,
    val width: String
)