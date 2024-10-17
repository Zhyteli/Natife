package com.test.natife

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.test.natife.ui.theme.NatifeTESTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//bopqh4oG9EcTcBdDd0O02rgLvvJQBU0z

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiphyList()
        }
    }
}

@Composable
fun GiphyList(viewModel: GiphyViewModel = hiltViewModel()) {
    val gifs: LazyPagingItems<com.test.natife.domain.models.GifObject> = viewModel.gifs.collectAsLazyPagingItems()

    LazyColumn {
        items(gifs.itemCount) { gif ->
            gifs[gif]?.let { GifItem(gif = it) }
        }

        // Обработка состояний загрузки и ошибок
        gifs.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    // Первый запуск или обновление данных
                    item { LoadingItem() }
                }
                loadState.append is LoadState.Loading -> {
                    // Подгрузка следующей страницы
                    item { LoadingItem() }
                }
                loadState.append is LoadState.Error -> {
                    val e = gifs.loadState.append as LoadState.Error
                    item {
                        ErrorItem(message = e.error.localizedMessage ?: "Error") {
                            gifs.retry()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun GifItem(gif: com.test.natife.domain.models.GifObject) {
    val imageUrl = gif.images.original.url
    val context = LocalContext.current

    // Create an ImageLoader with GIF support
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    // Use rememberAsyncImagePainter with the custom ImageLoader
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NatifeTESTTheme {
        Greeting("Android")
    }
}