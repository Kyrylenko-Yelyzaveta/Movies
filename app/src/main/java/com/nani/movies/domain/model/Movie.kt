package com.nani.movies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val description: String,
    val imageUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val rating: Double,
    val voteCount: Int,
    val popularity: Double,
    val originalLanguage: String,
    val categories: List<Int>,
    val isVideo: Boolean
) : Parcelable