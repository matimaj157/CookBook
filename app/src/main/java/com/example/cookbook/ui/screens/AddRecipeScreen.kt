package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cookbook.ui.CookBookViewModel

@Composable
fun AddRecipeScreen(viewModel: CookBookViewModel, onRecipeAdded: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    // 1. Stan przechowujący URI wybranego zdjęcia
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 2. Launcher do otwierania galerii
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFE0F7E9)).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nazwa") })

        // 3. Klikalny kontener na zdjęcie
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.DarkGray)
                .clickable { launcher.launch("image/*") }, // Otwiera galerię
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Text("Kliknij, aby dodać zdjęcie", color = Color.White)
            } else {
                // Wyświetla podgląd wybranego zdjęcia
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Opis") })

        Button(
            onClick = {
                if (name.isNotBlank()) {
                    viewModel.addRecipe(
                        name = name,
                        desc = description,
                        ingredients = ingredients.split(","),
                        uri = imageUri?.toString() // 4. Przekazujemy URI jako String do bazy
                    )
                    onRecipeAdded()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Dodaj przepis")
        }
    }
}