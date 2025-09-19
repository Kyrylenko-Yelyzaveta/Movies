package com.nani.movies.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nani.movies.domain.model.Movie
import com.nani.movies.domain.model.MovieCategory
import com.nani.movies.domain.usecase.GetMovieByIdUseCase
import com.nani.movies.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadMovies(category: MovieCategory = MovieCategory.POPULAR) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val movieList = getMoviesUseCase(category)
                _movies.value = movieList
            } catch (e: Exception) {
                Log.e("MovieViewModel", "loadMovies failed", e)
                _errorMessage.value = "Failed to load movies: ${e.message ?: "unknown"}"
            }
        }
    }
}

class MovieDetailViewModel(
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                val movie = getMovieByIdUseCase(movieId)
                _movie.value = movie
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "loadMovie failed for id: $movieId", e)
                _errorMessage.value = "Failed to load movie: ${e.message ?: "unknown"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}