package com.test.natife.data.mappers

import com.test.natife.data.models.base.GifObjectEntity
import com.test.natife.data.models.base.ImagesData
import com.test.natife.data.models.base.OriginalImageData
import com.test.natife.data.models.net.GifObjectNet
import com.test.natife.data.models.net.ImagesNet
import com.test.natife.data.models.net.OriginalImageNet

fun GifObjectEntity.toNet(): GifObjectNet {
    return GifObjectNet(
        id = id,
        images = imagesData.toNet(),
        isDeleted = isDeleted,
    )
}

fun GifObjectNet.toEntity(): GifObjectEntity {
    return GifObjectEntity(
        id = id,
        imagesData = images.toEntity(),
        isDeleted = isDeleted
    )
}



fun ImagesData.toNet(): ImagesNet {
    return ImagesNet(
        original = originalImageData.toNet()
    )
}

fun ImagesNet.toEntity(): ImagesData {
    return ImagesData(
        originalImageData = original.toEntity()
    )
}

fun OriginalImageData.toNet(): OriginalImageNet {
    return OriginalImageNet(
        url = url,
        height = height,
        width = width
    )
}
fun OriginalImageNet.toEntity(): OriginalImageData {
    return OriginalImageData(
        url = url,
        height = height,
        width = width
    )
}