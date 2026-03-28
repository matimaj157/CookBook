package com.example.cookbook.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val ingredients: List<String>, // Room użyje Converters
    val mediaUri: String? // Tu zapiszemy ścieżkę do zdjęcia/video
)
