package com.test.natife.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.natife.data.models.base.ImagesData
import com.test.natife.data.models.base.OriginalImageData

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun imagesDataToJson(imagesData: ImagesData): String {
        return gson.toJson(imagesData)
    }

    @TypeConverter
    fun jsonToImagesData(json: String): ImagesData {
        return gson.fromJson(json, ImagesData::class.java)
    }

    @TypeConverter
    fun originalImageDataToJson(originalImageData: OriginalImageData): String {
        return gson.toJson(originalImageData)
    }

    @TypeConverter
    fun jsonToOriginalImageData(json: String): OriginalImageData {
        return gson.fromJson(json, OriginalImageData::class.java)
    }
}