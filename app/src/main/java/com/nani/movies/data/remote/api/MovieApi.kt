package com.nani.movies.data.remote.api

import com.nani.movies.data.remote.dto.MovieDetailDto
import com.nani.movies.data.remote.dto.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto
}