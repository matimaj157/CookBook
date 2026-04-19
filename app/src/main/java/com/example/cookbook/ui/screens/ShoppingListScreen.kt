package com.example.cookbook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookbook.ui.CookBookViewModel

@Composable
fun ShoppingListScreen(viewModel: CookBookViewModel) {
    val items by viewModel.shoppingList.collectAsState(initial = emptyList())
    var newItemName by remember { mutableStateOf("") }
    val hasCheckedItems = items.any { it.isChecked }

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
                    placeholder = { Text("np. Mąka, Cukier...") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (newItemName.isNotBlank()) {
                            viewModel.addShoppingItem(newItemName)
                            newItemName = ""
                        }
                    })
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Lista zakupów", color = Color.White, fontSize = 22.sp)
                    
                    if (hasCheckedItems) {
                        Button(
                            onClick = { viewModel.checkoutCheckedItems() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF9D))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Kupione", color = Color.Black)
                        }
                    }
                }

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

                            Text(
                                text = item.Name,
                                color = if (item.isChecked) Color.Gray else Color.White,
                                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                                modifier = Modifier.weight(1f).padding(start = 8.dp)
                            )

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