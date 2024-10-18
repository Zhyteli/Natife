package com.test.natife.data.mappers

import com.test.natife.data.models.base.GifObjectEntity
import com.test.natife.data.models.base.ImagesData
import com.test.natife.data.models.base.OriginalImageData
import com.test.natife.domain.models.GifObject
import com.test.natife.domain.models.Images
import com.test.natife.domain.models.OriginalImage

fun GifObjectEntity.toDomain(): GifObject {
    return GifObject(
        id = id,
        images = imagesData.toDomain(),
        isDeleted = isDeleted
    )
}

fun GifObject.toEntity(): GifObjectEntity {
    return GifObjectEntity(
        id = id,
        imagesData = images.toEntity(),
        isDeleted = isDeleted
    )
}


fun ImagesData.toDomain(): Images {
    return Images(
        original = originalImageData.toDomain()
    )
}

fun Images.toEntity(): ImagesData {
    return ImagesData(
        originalImageData = original.toEntity()
    )
}

fun OriginalImageData.toDomain(): OriginalImage {
    return OriginalImage(
        url = url,
        height = height,
        width = width
    )
}
fun OriginalImage.toEntity(): OriginalImageData {
    return OriginalImageData(
        url = url,
        height = height,
        width = width
    )
}