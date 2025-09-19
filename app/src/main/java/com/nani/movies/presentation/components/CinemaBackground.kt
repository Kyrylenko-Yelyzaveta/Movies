package com.nani.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun CinemaBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D1117), // Dark navy
                        Color(0xFF1A1F2E), // Darker blue-gray
                        Color(0xFF2D1B3D), // Deep purple
                        Color(0xFF1A1F2E)  // Back to blue-gray
                    )
                )
            )
    ) {
        content()
    }
}