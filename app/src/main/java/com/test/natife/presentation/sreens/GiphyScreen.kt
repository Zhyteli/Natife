package com.test.natife.presentation.sreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.natife.data.models.net.isInternetAvailable
import com.test.natife.presentation.sreens.list.ListGifs
import com.test.natife.presentation.sreens.search.SearchInTextField
import com.test.natife.presentation.ui.theme.spacing
import com.test.natife.presentation.viewmodels.GiphyViewModel

@Composable
fun GiphyScreen(viewModel: GiphyViewModel = hiltViewModel()) {
    val gifs = viewModel.gifs.collectAsLazyPagingItems()
    var query by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column {
        SearchInTextField(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            onValueChange = { textFieldValue->
                if (isInternetAvailable(context)){
                    query = textFieldValue.text
                    viewModel.setQuery(textFieldValue.text)
                }
            }
        )

        ListGifs(gifs)
    }
}