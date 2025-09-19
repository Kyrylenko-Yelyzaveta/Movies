package com.nani.movies.data.mapper

import com.nani.movies.data.remote.dto.MovieDto
import com.nani.movies.data.remote.dto.MovieDetailDto
import com.nani.movies.domain.model.Movie

fun MovieDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    originalTitle = original_title,
    description = overview,
    imageUrl = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" },
    backdropUrl = backdrop_path?.let { "https://image.tmdb.org/t/p/w780$it" },
    releaseDate = release_date,
    rating = vote_average,
    voteCount = vote_count,
    popularity = popularity,
    originalLanguage = original_language,
    categories = genre_ids,
    isVideo = video
)

fun MovieDetailDto.toDomain(): Movie = Movie(
    id = id,
    title = title,
    originalTitle = original_title,
    description = overview,
    imageUrl = poster_path?.let { "https://image.tmdb.org/t/p/w500$it" },
    backdropUrl = backdrop_path?.let { "https://image.tmdb.org/t/p/w780$it" },
    releaseDate = release_date,
    rating = vote_average,
    voteCount = vote_count,
    popularity = popularity,
    originalLanguage = original_language,
    categories = genres.map { it.id },
    isVideo = video
)