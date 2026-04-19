/**
 * Entity representing a recipe in the application.
 * Includes details such as name, description, ingredients, and media assets.
 */
package com.example.cookbook.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val mediaUri: String?,
    val mediaType: String?
)
