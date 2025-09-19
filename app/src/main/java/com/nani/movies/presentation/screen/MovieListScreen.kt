package com.nani.movies.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nani.movies.R
import com.nani.movies.domain.model.Movie
import com.nani.movies.domain.model.MovieCategory
import com.nani.movies.presentation.components.CinemaBackground
import com.nani.movies.presentation.components.LoadingSkeleton
import com.nani.movies.presentation.components.MovieCard
import com.nani.movies.presentation.viewmodel.MovieViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieViewModel = koinViewModel()
) {
    val movies by viewModel.movies.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Segmented button state
    var selectedSegment by remember { mutableIntStateOf(0) }
    
    // Loading indicator
    var isLoading by remember { mutableStateOf(true) }

    val segmentTitles = listOf(
        stringResource(R.string.popular),
        stringResource(R.string.top_rated),
        stringResource(R.string.streaming)
    )

    val titleSuffix = stringResource(R.string.movies_suffix)

    // Observe movies state to detect when loading is finished
    LaunchedEffect(movies, errorMessage, selectedSegment) {
        isLoading = false
    }

    // API triggers on segment switch
    LaunchedEffect(selectedSegment) {
        isLoading = true
        when (selectedSegment) {
            0 -> viewModel.loadMovies(MovieCategory.POPULAR)
            1 -> viewModel.loadMovies(MovieCategory.TOP_RATED)
            2 -> viewModel.loadMovies(MovieCategory.NOW_PLAYING)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("${segmentTitles[selectedSegment]} $titleSuffix") }
            )
        }
    ) { paddingValues ->
        CinemaBackground {
            Column(modifier = Modifier.padding(paddingValues)) {
                // Segmented buttons
                CategorySegmentedButtons(
                    selectedSegment = selectedSegment,
                    segmentTitles = segmentTitles,
                    onSegmentSelected = { selectedSegment = it }
                )
                
                Spacer(modifier = Modifier.height(10.dp))
                
                when {
                    isLoading -> {
                        LoadingSkeleton()
                    }
                    !errorMessage.isNullOrEmpty() -> {
                        ErrorMessage(errorMessage = errorMessage ?: "")
                    }
                    else -> {
                        MovieList(
                            movies = movies,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategorySegmentedButtons(
    selectedSegment: Int,
    segmentTitles: List<String>,
    onSegmentSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SingleChoiceSegmentedButtonRow {
            segmentTitles.forEachIndexed { index, title ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = segmentTitles.size
                    ),
                    onClick = { onSegmentSelected(index) },
                    selected = selectedSegment == index,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorMessage(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = errorMessage,
        color = MaterialTheme.colorScheme.error,
        modifier = modifier.padding(16.dp)
    )
}

@Composable
private fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                onClick = onMovieClick
            )
        }
    }
}