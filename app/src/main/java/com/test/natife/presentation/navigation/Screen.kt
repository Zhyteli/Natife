package com.test.natife.presentation.navigation

sealed class Screen(val route: String) {
    data object GifList : Screen("gif_list")
    data object GifDetail : Screen("gif_detail/{startIndex}") {
        fun createRoute(startIndex: Int) = "gif_detail/$startIndex"
    }
}
