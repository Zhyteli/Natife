package com.test.natife.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.natife.presentation.sreens.GifDetailScreen
import com.test.natife.presentation.sreens.GiphyScreen

@Composable
fun GifNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        // Screen with a list of GIFs
        composable("list") {
            GiphyScreen(navController)
        }

        // Screen for viewing one GIF
        composable("gifDetail/{initialIndex}") { backStackEntry ->
            val initialIndex = backStackEntry.arguments?.getString("initialIndex")?.toIntOrNull() ?: 0
            GifDetailScreen(navController = navController, initialIndex = initialIndex)
        }
    }
}