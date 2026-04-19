/**
 * Defines the destinations (routes) used for navigation within the application.
 * Using Kotlin Serialization for type-safe navigation.
 */
package com.example.cookbook.navigation

import kotlinx.serialization.Serializable

@Serializable
object Welcome

@Serializable
object RecipesList

@Serializable
data class RecipeDetails(val recipeId: Int)

@Serializable
object ShoppingList

@Serializable
object AddRecipe

@Serializable
data class EditRecipe(val recipeId: Int)

@Serializable
object Pantry
