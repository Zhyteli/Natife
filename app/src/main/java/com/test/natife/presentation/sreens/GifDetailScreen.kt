package com.test.natife.presentation.sreens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.test.natife.domain.models.GifObject
import com.test.natife.presentation.models.GifObjectUi
import com.test.natife.presentation.viewmodels.GiphyViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GifDetailScreen(
    navController: NavHostController,
    initialIndex: Int,
    viewModel: GiphyViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(initialPage = initialIndex)
    val gifs = viewModel.gifs.collectAsLazyPagingItems()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            count = gifs.itemCount,
            state = pagerState
        ) { page ->
            gifs[page]?.let { gif ->
                GifDetailItem(gif = gif)
            }
        }
    }
}

@Composable
fun GifDetailItem(gif: GifObjectUi) {
    val imageUrl = gif.imagesUi.original.url
    val imageSize = gif.imagesUi.original
    val context = LocalContext.current

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
            .crossfade(true)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(
                height = imageSize.height.toInt().dp,
                width = imageSize.width.toInt().dp
            )
            .padding(16.dp)
    )
}