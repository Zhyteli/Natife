package com.test.natife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.natife.presentation.sreens.GifDetailScreen
import com.test.natife.presentation.sreens.GiphyScreen
import com.test.natife.presentation.sreens.list.ListGifs

@Composable
fun GifNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        // Экран со списком GIF
        composable("list") {
            GiphyScreen(navController)
        }

        // Экран для просмотра одного GIF с возможностью свайпа
        composable("gifDetail/{initialIndex}") { backStackEntry ->
            val initialIndex = backStackEntry.arguments?.getString("initialIndex")?.toIntOrNull() ?: 0
            GifDetailScreen(navController = navController, initialIndex = initialIndex)
        }
    }
}