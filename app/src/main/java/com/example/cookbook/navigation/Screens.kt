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
object Pantry
