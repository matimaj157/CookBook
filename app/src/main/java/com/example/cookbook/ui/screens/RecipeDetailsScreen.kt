package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookbook.ui.CookBookViewModel
import coil.compose.AsyncImage

@Composable
fun RecipeDetailsScreen(recipeId: Int, viewModel: CookBookViewModel) {
    // Pobieramy wszystkie przepisy i szukamy tego jednego o konkretnym ID
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())
    val recipe = recipes.find { it.id == recipeId }

    recipe?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7E9))
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = it.mediaUri,
                contentDescription = "Zdjęcie dania",
                contentScale = ContentScale.Crop, // Gwarantuje, że zdjęcie ładnie wypełni prostokąt
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = it.name, fontSize = 28.sp, color = Color.Black, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                // Dodano Modifier.fillMaxWidth() do karty
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