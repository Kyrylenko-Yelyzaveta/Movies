package com.nani.movies.domain.repository

import com.nani.movies.domain.model.Movie
import com.nani.movies.domain.model.MovieCategory

interface MovieRepository {
    suspend fun getMovies(category: MovieCategory): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie
}