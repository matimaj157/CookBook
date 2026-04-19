/**
 * Data Access Object (DAO) for the CookBook application.
 * Provides methods for interacting with the local SQLite database.
 */
package com.example.cookbook.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import com.example.cookbook.data.local.entities.Recipe
import com.example.cookbook.data.local.entities.ShoppingListItem
import com.example.cookbook.data.local.entities.PantryItem

@Dao
interface CookBookDao {
    // --- Recipes ---

    /** Retrieves all stored recipes. */
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    /** Retrieves a single recipe by its ID. */
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    /** Inserts a new recipe. */
    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    /** Updates an existing recipe. */
    @Update
    suspend fun updateRecipe(recipe: Recipe)

    /** Deletes a recipe. */
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    // --- Shopping List ---

    /** Retrieves all shopping list items. */
    @Query("SELECT * FROM shopping_list")
    fun getShoppingList(): Flow<List<ShoppingListItem>>

    /** Retrieves all items marked as checked in the shopping list. */
    @Query("SELECT * FROM shopping_list WHERE isChecked = 1")
    suspend fun getCheckedShoppingItems(): List<ShoppingListItem>

    /** Adds an item to the shopping list. */
    @Insert
    suspend fun addToShoppingList(item: ShoppingListItem)

    /** Updates a shopping list item. */
    @Update
    suspend fun updateShoppingItem(item: ShoppingListItem)

    /** Deletes a shopping list item. */
    @Delete
    suspend fun deleteShoppingItem(item: ShoppingListItem)

    /** Deletes all checked items from the shopping list. */
    @Query("DELETE FROM shopping_list WHERE isChecked = 1")
    suspend fun deleteCheckedShoppingItems()

    // --- Pantry ---

    /** Retrieves all pantry items. */
    @Query("SELECT * FROM pantry")
    fun getPantryItems(): Flow<List<PantryItem>>

    /** Adds an item to the pantry. */
    @Insert
    suspend fun addToPantry(item: PantryItem)

    /** Deletes an item from the pantry by its ID. */
    @Query("DELETE FROM pantry WHERE id = :id")
    suspend fun deleteFromPantry(id: Int)
}