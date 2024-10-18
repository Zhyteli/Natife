package com.test.natife.data.models.base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifs")
data class GifObjectEntity(
    @PrimaryKey
    val id: String,
    val imagesData: ImagesData,
    val isDeleted: Boolean = false
)

data class ImagesData(
    val originalImageData: OriginalImageData
)

data class OriginalImageData(
    val url: String,
    val height: String,
    val width: String
)