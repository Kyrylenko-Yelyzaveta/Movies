package com.nani.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSkeleton(
    modifier: Modifier = Modifier,
    itemCount: Int = 7
) {
    LazyColumn(modifier = modifier) {
        items(itemCount) {
            SkeletonCard()
        }
    }
}

@Composable
private fun SkeletonCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image skeleton
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color(0xFF2D1B3D).copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                // Title skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(18.dp)
                        .background(
                            color = Color(0xFF2D1B3D).copy(alpha = 0.3f),
                            shape = RoundedCornerShape(5.dp)
                        )
                )
                // Description skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .height(14.dp)
                        .background(
                            color = Color(0xFF2D1B3D).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(3.dp)
                        )
                )
                // Rating skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(12.dp)
                        .background(
                            color = Color(0xFF2D1B3D).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(3.dp)
                        )
                )
            }
        }
    }
}