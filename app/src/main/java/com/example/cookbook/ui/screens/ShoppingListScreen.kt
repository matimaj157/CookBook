package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookbook.ui.CookBookViewModel

@Composable
fun ShoppingListScreen(viewModel: CookBookViewModel) {
    val items by viewModel.shoppingList.collectAsState(initial = emptyList())
    var newItemName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7E9))
            .padding(16.dp)
    ) {
        // Karta z polem do dodawania nowych elementów
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
                    placeholder = { Text("np. Mąka, Cukier...") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                )
                IconButton(onClick = {
                    if (newItemName.isNotBlank()) {
                        viewModel.addShoppingItem(newItemName)
                        newItemName = ""
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Dodaj produkt",
                        tint = Color(0xFF00FF9D)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Karta wyświetlająca listę elementów
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Lista zakupów", color = Color.White, fontSize = 22.sp)

                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    items(items) { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { viewModel.toggleShoppingItem(item) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF00FF9D),
                                    uncheckedColor = Color.White,
                                    checkmarkColor = Color.Black
                                )
                            )

                            // Przekreślenie tekstu, jeśli checkbox jest zaznaczony
                            Text(
                                text = item.Name,
                                color = if (item.isChecked) Color.Gray else Color.White,
                                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                                modifier = Modifier.weight(1f).padding(start = 8.dp)
                            )

                            // Przycisk usuwania z listy zakupów
                            IconButton(onClick = { viewModel.deleteShoppingItem(item) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Usuń element",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}