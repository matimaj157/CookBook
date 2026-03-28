package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(250.dp).background(Color.LightGray)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = it.name, fontSize = 28.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF333333))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Składniki:", color = Color.White, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                        it.ingredients.forEach { ingredient ->
                            Text("• $ingredient", color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Przygotowanie:", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                Text(text = it.description, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}