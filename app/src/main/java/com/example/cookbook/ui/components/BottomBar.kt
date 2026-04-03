package com.example.cookbook.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cookbook.navigation.RecipesList
import com.example.cookbook.navigation.ShoppingList
import com.example.cookbook.navigation.AddRecipe
import com.example.cookbook.navigation.Pantry

@Composable
fun BottomBar(navController: NavHostController, shoppingItemCount: Int) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentRoute?.contains("RecipesList") == true,
            onClick = { navController.navigate(RecipesList) },
            label = { Text("Przepisy", color = Color.White) },
            icon = { Icon(Icons.Default.List, contentDescription = null, tint = Color(0xFF00FF9D)) }
        )

        NavigationBarItem(
            selected = currentRoute?.contains("ShoppingList") == true,
            onClick = { navController.navigate(ShoppingList) },
            label = { Text("Zakupy", color = Color.White) },
            icon = {
                if (shoppingItemCount > 0) {
                    BadgedBox(badge = { Badge { Text(shoppingItemCount.toString()) } }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color(0xFF00FF9D))
                    }
                } else {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color(0xFF00FF9D))
                }
            }
        )

        NavigationBarItem(
            selected = currentRoute?.contains("AddRecipe") == true,
            onClick = { navController.navigate(AddRecipe) },
            label = { Text("Dodaj", color = Color.White) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color(0xFF00FF9D)) }
        )

        NavigationBarItem(
            selected = currentRoute?.contains("Pantry") == true,
            onClick = { navController.navigate(Pantry) },
            label = { Text("Spiżarka", color = Color.White) },
            icon = { Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF00FF9D)) }
        )
    }
}
