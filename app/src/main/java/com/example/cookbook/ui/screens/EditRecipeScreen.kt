/**
 * A Composable that allows users to edit an existing recipe.
 * Loads current recipe data and provides a form to update its details and media.
 */
package com.example.cookbook.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cookbook.ui.CookBookViewModel
import kotlinx.coroutines.launch

/**
 * Renders the recipe editing form and handles the update process.
 */
@Composable
fun EditRecipeScreen(recipeId: Int, viewModel: CookBookViewModel, onRecipeUpdated: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var mediaType by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(recipeId) {
        val recipe = viewModel.getRecipeById(recipeId)
        recipe?.let {
            name = it.name
            description = it.description
            ingredients = it.ingredients.joinToString(", ")
            imageUri = it.mediaUri?.let { uri -> Uri.parse(uri) }
            mediaType = it.mediaType
        }
        isLoading = false
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageUri = it
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(it)
            mediaType = if (mimeType?.startsWith("video") == true) "VIDEO" else "IMAGE"
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF00FF9D))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7E9))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it.replace("\n", "") },
                label = { Text("Nazwa przepisu") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.DarkGray)
                    .clickable { launcher.launch(arrayOf("image/*", "video/*")) },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    Text("Kliknij, aby zmienić zdjęcie lub wideo", color = Color.White)
                } else if (mediaType == "VIDEO") {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Wybrano wideo", color = Color.White)
                    }
                } else {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Wybrane zdjęcie",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Składniki (oddziel przecinkami)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Sposób przygotowania") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        scope.launch {
                            val existingRecipe = viewModel.getRecipeById(recipeId)
                            existingRecipe?.let {
                                val updatedRecipe = it.copy(
                                    name = name,
                                    description = description,
                                    ingredients = ingredients.split(",").map { i -> i.trim() }.filter { i -> i.isNotBlank() },
                                    mediaUri = imageUri?.toString(),
                                    mediaType = mediaType
                                )
                                viewModel.updateRecipe(updatedRecipe)
                                onRecipeUpdated()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Zapisz zmiany", color = Color(0xFF00FF9D))
            }
        }
    }
}
