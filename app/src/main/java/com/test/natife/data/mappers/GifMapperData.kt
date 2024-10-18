package com.test.natife.data.mappers

import com.test.natife.data.models.base.GifObjectData
import com.test.natife.data.models.base.ImagesData
import com.test.natife.data.models.base.OriginalImageData
import com.test.natife.domain.models.GifObject
import com.test.natife.domain.models.Images
import com.test.natife.domain.models.OriginalImage

fun GifObjectData.toDomain(): GifObject {
    return GifObject(
        id = id,
        images = imagesData.toDomain()
    )
}

fun ImagesData.toDomain(): Images {
    return Images(
        original = originalImageData.toDomain()
    )
}

fun OriginalImageData.toDomain(): OriginalImage {
    return OriginalImage(
        url = url,
        height = height,
        width = width
    )
}