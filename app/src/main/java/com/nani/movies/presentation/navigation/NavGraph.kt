package com.nani.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nani.movies.presentation.screen.MovieDetailScreen
import com.nani.movies.presentation.screen.MovieListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MovieList.route,
        modifier = modifier
    ) {
        composable(Screen.MovieList.route) {
            MovieListScreen(
                onMovieClick = { movie ->
                    navController.navigate(Screen.MovieDetail.createRoute(movie.id))
                }
            )
        }

        composable(Screen.MovieDetail.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()

            if (movieId != null) {
                MovieDetailScreen(
                    movieId = movieId,
                    onBack = { navController.popBackStack() }
                )
            } else {
                // This should not happen in normal circumstances, but handle gracefully
                navController.popBackStack()
            }
        }
    }
}

