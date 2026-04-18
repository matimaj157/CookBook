package com.example.cookbook.ui.screens

import android.media.AudioManager
import android.media.ToneGenerator
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

@Composable
fun AddRecipeScreen(viewModel: CookBookViewModel, onRecipeAdded: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    // Stan przechowujący URI wybranego zdjęcia
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher do otwierania galerii (wymaga odpowiednich importów ActivityResultContracts)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7E9))
            .verticalScroll(rememberScrollState()) // Dodano scrollowanie formularza
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

        // Klikalny kontener na zdjęcie
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.DarkGray)
                .clickable { launcher.launch("image/*") }, // Otwiera galerię zdjęć
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Text("Kliknij, aby dodać zdjęcie", color = Color.White)
            } else {
                // Wyświetla podgląd wybranego zdjęcia (wymaga importu AsyncImage z Coil)
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Wybrane zdjęcie",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Zdjęcie wypełni cały prostokąt
                )
            }
        }

        // Dodane pole do wprowadzania składników
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
                    // Odtworzenie dźwięku potwierdzenia
                    try {
                        val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
                        toneGen.startTone(ToneGenerator.TONE_PROP_ACK, 200)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    viewModel.addRecipe(
                        name = name,
                        desc = description,
                        // Dzielimy po przecinku, usuwamy ewentualne puste spacje i pomijamy puste fragmenty
                        ingredients = ingredients.split(",").map { it.trim() }.filter { it.isNotBlank() },
                        uri = imageUri?.toString()
                    )
                    onRecipeAdded()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Dodaj przepis", color = Color(0xFF00FF9D))
        }
    }
}