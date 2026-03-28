package com.example.cookbook.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import com.example.cookbook.data.local.entities.Recipe
import com.example.cookbook.data.local.entities.ShoppingListItem
import com.example.cookbook.data.local.entities.PantryItem

@Dao
interface CookBookDao {
    // PRZEPISY
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    // LISTA ZAKUPÓW
    @Query("SELECT * FROM shopping_list")
    fun getShoppingList(): Flow<List<ShoppingListItem>>

    @Insert
    suspend fun addToShoppingList(item: ShoppingListItem)

    // SPIŻARNIA
    @Query("SELECT * FROM pantry")
    fun getPantryItems(): Flow<List<PantryItem>>

    @Insert
    suspend fun addToPantry(item: PantryItem)

    @Query("DELETE FROM pantry WHERE id = :id")
    suspend fun deleteFromPantry(id: Int)
}