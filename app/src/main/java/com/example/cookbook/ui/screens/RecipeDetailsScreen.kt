/**
 * A Composable that displays the full details of a specific recipe,
 * including its ingredients, preparation steps, and media (image or video).
 */
package com.example.cookbook.ui.screens

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.cookbook.ui.CookBookViewModel

/**
 * Displays detailed information about a recipe and provides editing/deletion options.
 */
@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    viewModel: CookBookViewModel,
    onEditClick: (Int) -> Unit,
    onDeleteSuccess: () -> Unit
) {
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())
    val recipe = recipes.find { it.id == recipeId }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog && recipe != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Usuń przepis") },
            text = { Text("Czy na pewno chcesz usunąć przepis \"${recipe.name}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteRecipe(recipe)
                        showDeleteDialog = false
                        onDeleteSuccess()
                    }
                ) {
                    Text("Usuń", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Anuluj")
                }
            }
        )
    }

    recipe?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7E9))
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                if (it.mediaType == "VIDEO" && it.mediaUri != null) {
                    var isVideoFinished by remember { mutableStateOf(false) }
                    var videoViewRef by remember { mutableStateOf<VideoView?>(null) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        AndroidView(
                            factory = { context ->
                                VideoView(context).apply {
                                    setVideoURI(Uri.parse(it.mediaUri))
                                    setOnCompletionListener {
                                        isVideoFinished = true
                                    }
                                    videoViewRef = this
                                    start()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )

                        if (isVideoFinished) {
                            IconButton(
                                onClick = {
                                    isVideoFinished = false
                                    videoViewRef?.start()
                                },
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Odtwórz ponownie",
                                    tint = Color.White,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    }
                } else {
                    AsyncImage(
                        model = it.mediaUri,
                        contentDescription = "Zdjęcie dania",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.LightGray)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onEditClick(it.id) },
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edytuj", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Usuń", tint = Color.Red)
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = it.name, fontSize = 28.sp, color = Color.Black, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Składniki:",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        it.ingredients.forEach { ingredient ->
                            Text("• $ingredient", color = Color.White, modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Przygotowanie:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = it.description,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.DarkGray,
                    lineHeight = 24.sp
                )
            }
        }
    }
}
