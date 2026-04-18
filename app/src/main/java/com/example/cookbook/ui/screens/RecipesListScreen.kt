package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cookbook.ui.CookBookViewModel
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment

@Composable
fun RecipesListScreen(viewModel: CookBookViewModel, onRecipeClick: (Int) -> Unit) {
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE0F7E9)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes) { recipe ->
            Card(
                onClick = { onRecipeClick(recipe.id) },
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(180.dp)
                    ) {
                        AsyncImage(
                            model = recipe.mediaUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                        if (recipe.mediaType == "VIDEO") {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Wideo",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(48.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Text(
                        text = recipe.name,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}