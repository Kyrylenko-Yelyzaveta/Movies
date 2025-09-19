package com.nani.movies.data.remote.dto

import com.squareup.moshi.Json

data class MovieListResponse(
    @Json(name = "results") val results: List<MovieDto>
)