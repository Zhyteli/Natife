package com.test.natife.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.test.natife.presentation.navigation.GifNavGraph
import com.test.natife.presentation.ui.theme.NatifeTESTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//my key: bopqh4oG9EcTcBdDd0O02rgLvvJQBU0z

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NatifeTESTTheme {
                GifNavGraph()
            }
        }
    }
}