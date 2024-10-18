package com.test.natife.presentation.models

data class GifObjectUi(
    val id: String,
    val imagesUi: ImagesUi,
    val isDeleted: Boolean = false
)

data class ImagesUi(
    val original: OriginalImageUi
)

data class OriginalImageUi(
    val url: String,
    val height: String,
    val width: String
)