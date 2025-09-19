package com.nani.movies.data.repository

import android.util.Log
import com.nani.movies.data.mapper.toDomain
import com.nani.movies.data.remote.api.MovieApi
import com.nani.movies.domain.model.Movie
import com.nani.movies.domain.model.MovieCategory
import com.nani.movies.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getMovies(category: MovieCategory): List<Movie> =
        withContext(Dispatchers.IO) {
            try {
                when (category) {
                    MovieCategory.POPULAR -> {
                        val response = api.getPopularMovies()
                        response.results.map { it.toDomain() }
                    }

                    MovieCategory.TOP_RATED -> {
                        val response = api.getTopRatedMovies()
                        response.results.map { it.toDomain() }
                    }

                    MovieCategory.NOW_PLAYING -> {
                        val response = api.getNowPlayingMovies()
                        response.results.map { it.toDomain() }
                    }
                }
            } catch (e: Exception) {
                Log.e("MovieRepository", "getMovies failed", e)
                throw e
            }
        }

    override suspend fun getMovieById(movieId: Int): Movie =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getMovieById(movieId)
                response.toDomain()
            } catch (e: Exception) {
                Log.e("MovieRepository", "getMovieById failed for id: $movieId", e)
                throw e
            }
        }
}