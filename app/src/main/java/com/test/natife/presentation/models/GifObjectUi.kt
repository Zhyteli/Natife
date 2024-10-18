package com.test.natife.presentation.models

data class GifObjectUi(
    val id: String,
    val imagesUi: ImagesUi
)

data class ImagesUi(
    val original: OriginalImageUi
)

data class OriginalImageUi(
    val url: String
)