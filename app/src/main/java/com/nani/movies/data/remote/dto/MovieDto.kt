package com.nani.movies.data.remote.dto

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "original_title") val original_title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val poster_path: String?,
    @Json(name = "backdrop_path") val backdrop_path: String?,
    @Json(name = "release_date") val release_date: String?,
    @Json(name = "vote_average") val vote_average: Double,
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "original_language") val original_language: String,
    @Json(name = "genre_ids") val genre_ids: List<Int>,
    @Json(name = "video") val video: Boolean
)

data class MovieDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "original_title") val original_title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val poster_path: String?,
    @Json(name = "backdrop_path") val backdrop_path: String?,
    @Json(name = "release_date") val release_date: String?,
    @Json(name = "vote_average") val vote_average: Double,
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "original_language") val original_language: String,
    @Json(name = "genres") val genres: List<GenreDto>,
    @Json(name = "video") val video: Boolean
)

data class GenreDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)