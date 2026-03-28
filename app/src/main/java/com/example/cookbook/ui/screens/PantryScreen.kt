package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookbook.ui.CookBookViewModel

@Composable
fun PantryScreen(viewModel: CookBookViewModel) {
    // Reaktywne pobieranie danych z bazy
    val items by viewModel.pantryItems.collectAsState(initial = emptyList())
    var newItemName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7E9))
            .padding(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newItemName,
                    onValueChange = { newItemName = it },
                    placeholder = { Text("np. Mleko, Jajka...") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                )
                IconButton(onClick = {
                    if (newItemName.isNotBlank()) {
                        viewModel.addPantryItem(newItemName)
                        newItemName = ""
                    }
                }) {
                    Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color(0xFF00FF9D))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text("Spiżarka", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(bottom = 12.dp))
                }
                items(items) { product ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(product.Name, color = Color.White)
                        Row {
                            IconButton(onClick = { /* Przenieś do zakupów */ }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White)
                            }
                            IconButton(onClick = { viewModel.deletePantryItem(product.id) }) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}