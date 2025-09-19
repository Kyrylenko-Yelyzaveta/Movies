package com.nani.movies.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nani.movies.R
import com.nani.movies.domain.model.Movie
import com.nani.movies.presentation.components.CinemaBackground
import com.nani.movies.presentation.viewmodel.MovieDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModel = koinViewModel()
) {
    val movie by viewModel.movie.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        movie?.title ?: stringResource(R.string.loading),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        CinemaBackground {
            when {
                isLoading -> {
                    LoadingContent(modifier = Modifier.padding(paddingValues))
                }
                errorMessage != null -> {
                    ErrorContent(
                        errorMessage = errorMessage!!,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                movie != null -> {
                    MovieContent(
                        movie = movie!!,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                else -> {
                    MovieNotFoundContent(modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun MovieNotFoundContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.movie_not_found),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun MovieContent(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            BackdropImage(movie = movie)
        }

        item {
            MovieDetailsContent(movie = movie)
        }
    }
}

@Composable
private fun BackdropImage(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = movie.backdropUrl ?: movie.imageUrl,
        contentDescription = stringResource(R.string.movie_backdrop),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
private fun MovieDetailsContent(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        MovieTitleSection(movie = movie)
        Spacer(modifier = Modifier.height(16.dp))

        RatingAndPopularitySection(movie = movie)
        Spacer(modifier = Modifier.height(16.dp))

        ReleaseDateAndLanguageSection(movie = movie)
        Spacer(modifier = Modifier.height(24.dp))

        OverviewSection(movie = movie)
        Spacer(modifier = Modifier.height(16.dp))

        PosterSection(movie = movie)
    }
}

@Composable
private fun MovieTitleSection(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        if (movie.originalTitle != movie.title) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.original_title, movie.originalTitle),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFB3B3B3)
            )
        }
    }
}

@Composable
private fun RatingAndPopularitySection(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.rating_with_votes, movie.rating, movie.voteCount),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.vote_count, movie.voteCount),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB3B3B3)
            )
        }

        Column {
            Text(
                text = stringResource(R.string.popularity_label),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB3B3B3)
            )
            Text(
                text = stringResource(R.string.popularity_value, movie.popularity),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ReleaseDateAndLanguageSection(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.release_date_label),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB3B3B3)
            )
            Text(
                text = movie.releaseDate ?: stringResource(R.string.unknown),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }

        Column {
            Text(
                text = stringResource(R.string.language_label),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFB3B3B3)
            )
            Text(
                text = movie.originalLanguage.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun OverviewSection(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.overview_label),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFE0E0E0)
        )
    }
}

@Composable
private fun PosterSection(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.poster_label),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = movie.imageUrl,
            contentDescription = stringResource(R.string.poster_description, movie.title),
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
        )
    }
}