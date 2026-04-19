/**
 * A Composable that displays the user's pantry/storage items.
 * Users can manage what items they have in stock and move items to the shopping list.
 */
package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookbook.ui.CookBookViewModel

/**
 * Displays the pantry UI and allows for item management.
 */
@Composable
fun PantryScreen(viewModel: CookBookViewModel) {
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
                    onValueChange = {
                        newItemName = it.replace("\n", "") 
                    },
                    placeholder = { Text("np. Mleko, Jajka...") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (newItemName.isNotBlank()) {
                            viewModel.addPantryItem(newItemName)
                            newItemName = ""
                        }
                    })
                )
                IconButton(onClick = {
                    if (newItemName.isNotBlank()) {
                        viewModel.addPantryItem(newItemName)
                        newItemName = ""
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Dodaj do spiżarki",
                        tint = Color(0xFF00FF9D)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Spiżarka",
                    color = Color.White,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyColumn {
                    items(items) { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = product.Name, color = Color.White)
                            Row {
                                IconButton(onClick = {
                                    viewModel.addShoppingItem(product.Name)
                                    viewModel.deletePantryItem(product.id)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Przenieś do zakupów",
                                        tint = Color(0xFF00FF9D)
                                    )
                                }

                                IconButton(onClick = { viewModel.deletePantryItem(product.id) }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Usuń ze spiżarki",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}