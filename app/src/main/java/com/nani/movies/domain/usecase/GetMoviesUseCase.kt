package com.nani.movies.domain.usecase

import com.nani.movies.domain.model.Movie
import com.nani.movies.domain.model.MovieCategory
import com.nani.movies.domain.repository.MovieRepository

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(category: MovieCategory): List<Movie> {
        return repository.getMovies(category)
    }
}

class GetMovieByIdUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return repository.getMovieById(movieId)
    }
}