package com.nani.movies.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.nani.movies.presentation.navigation.NavGraph
import com.nani.movies.presentation.theme.MoviesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MoviesTheme {
                MovieApp()
            }
        }
    }
}

@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavGraph(navController = navController)
}