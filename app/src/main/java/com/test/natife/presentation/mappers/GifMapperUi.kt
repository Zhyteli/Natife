package com.test.natife.presentation.mappers

import com.test.natife.domain.models.GifObject
import com.test.natife.domain.models.Images
import com.test.natife.domain.models.OriginalImage
import com.test.natife.presentation.models.GifObjectUi
import com.test.natife.presentation.models.ImagesUi
import com.test.natife.presentation.models.OriginalImageUi

fun GifObjectUi.toDomain(): GifObject {
    return GifObject(
        id = id,
        images = imagesUi.toDomain(),
        isDeleted = isDeleted
    )
}

fun GifObject.toUi(): GifObjectUi {
    return GifObjectUi(
        id = id,
        imagesUi = images.toUi(),
        isDeleted = isDeleted
    )
}


fun ImagesUi.toDomain(): Images {
    return Images(
        original = original.toDomain()
    )
}

fun Images.toUi(): ImagesUi {
    return ImagesUi(
        original = original.toUi()
    )
}

fun OriginalImageUi.toDomain(): OriginalImage {
    return OriginalImage(
        url = url,
        height = height,
        width = width
    )
}
fun OriginalImage.toUi(): OriginalImageUi {
    return OriginalImageUi(
        url = url,
        height = height,
        width = width
    )
}