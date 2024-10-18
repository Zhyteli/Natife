package com.test.natife.presentation.sreens.list

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.wear.compose.material.ContentAlpha
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.test.natife.domain.models.GifObject
import com.test.natife.domain.models.Images
import com.test.natife.domain.models.OriginalImage

@Composable
fun ListGifs(gifs: LazyPagingItems<GifObject>) {
    // Оптимизированная LazyGrid с StaggeredGridCells
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(gifs.itemCount) { index ->
            gifs[index]?.let { gif ->
                // Используем ключ для уменьшения перерисовок
                key(gif.id) {
                    GifItem(gif = gif)
                }
            }
        }

        gifs.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.append is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    item {
                        ErrorItem(message = e.error.localizedMessage ?: "Unknown Error") {
                            retry()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GifItem(gif: GifObject) {
    val imageUrl = gif.images.original.url
    val context = LocalContext.current

    // Оптимизация через remember для предотвращения лишних перерисовок
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true) // Плавная смена изображений
            .build(),
        imageLoader = imageLoader
    )

    // Используем derivedStateOf для уменьшения пересчётов aspectRatio
    val aspectRatio by remember { derivedStateOf { gif.images.original.width.toFloat() / gif.images.original.height.toFloat() } }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GifItemPreview() {
    GifItem(
        GifObject(
            "555",
            Images(
                OriginalImage(
                    "https://giphy.com/gifs/hallmarkecards-cute-hallmark-shoebox-BzyTuYCmvSORqs1ABM",
                    "400",
                    "400"
                )
            )
        )
    )
}


@Composable
fun LoadingItem() {
    CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = message)
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}