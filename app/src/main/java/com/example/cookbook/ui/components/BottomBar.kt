/**
 * A Composable that displays the bottom navigation bar for the application.
 * Provides navigation between the main screens: Recipes, Shopping List, Add Recipe, and Pantry.
 */
package com.example.cookbook.ui.components

import android.view.SoundEffectConstants
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cookbook.navigation.RecipesList
import com.example.cookbook.navigation.ShoppingList
import com.example.cookbook.navigation.AddRecipe
import com.example.cookbook.navigation.Pantry

/**
 * Renders the navigation bar and handles route selection and click sounds.
 */
@Composable
fun BottomBar(navController: NavHostController, shoppingItemCount: Int) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val view = LocalView.current

    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentRoute?.contains("RecipesList") == true,
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                navController.navigate(RecipesList)
            },
            label = { Text("Przepisy", color = Color.White) },
            icon = { Icon(Icons.Default.List, contentDescription = null, tint = Color(0xFF00FF9D)) }
        )

        NavigationBarItem(
            selected = currentRoute?.contains("ShoppingList") == true,
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                navController.navigate(ShoppingList)
            },
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
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                navController.navigate(AddRecipe)
            },
            label = { Text("Dodaj", color = Color.White) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color(0xFF00FF9D)) }
        )

        NavigationBarItem(
            selected = currentRoute?.contains("Pantry") == true,
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                navController.navigate(Pantry)
            },
            label = { Text("Spiżarka", color = Color.White) },
            icon = { Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF00FF9D)) }
        )
    }
}
