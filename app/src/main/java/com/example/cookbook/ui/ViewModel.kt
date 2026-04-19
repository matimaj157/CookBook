/**
 * ViewModel for the CookBook application.
 * Manages the state and business logic for recipes, shopping lists, and pantry items.
 */
package com.example.cookbook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import com.example.cookbook.data.local.dao.CookBookDao
import com.example.cookbook.data.local.entities.PantryItem
import com.example.cookbook.data.local.entities.Recipe
import com.example.cookbook.data.local.entities.ShoppingListItem

class CookBookViewModel(private val dao: CookBookDao) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    /**
     * StateFlow containing the current search query for filtering recipes.
     */
    val searchQuery: StateFlow<String> = _searchQuery

    /**
     * StateFlow of filtered recipes based on the search query.
     */
    val recipes: StateFlow<List<Recipe>> = dao.getAllRecipes()
        .combine(_searchQuery) { recipes, query ->
            if (query.isBlank()) {
                recipes
            } else {
                recipes.filter { it.name.contains(query, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Flow of all shopping list items.
     */
    val shoppingList: Flow<List<ShoppingListItem>> = dao.getShoppingList()
    /**
     * Flow of all pantry items.
     */
    val pantryItems: Flow<List<PantryItem>> = dao.getPantryItems()

    /**
     * Updates the search query for recipe filtering.
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Adds a new recipe to the database.
     */
    fun addRecipe(name: String, desc: String, ingredients: List<String>, uri: String?, type: String? = null) {
        viewModelScope.launch {
            dao.insertRecipe(Recipe(name = name, description = desc, ingredients = ingredients, mediaUri = uri, mediaType = type))
        }
    }

    /**
     * Updates an existing recipe.
     */
    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            dao.updateRecipe(recipe)
        }
    }

    /**
     * Deletes a recipe from the database.
     */
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            dao.deleteRecipe(recipe)
        }
    }

    /**
     * Retrieves a recipe by its unique identifier.
     */
    suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }

    /**
     * Adds a new item to the pantry.
     */
    fun addPantryItem(name: String) {
        viewModelScope.launch {
            dao.addToPantry(PantryItem(Name = name))
        }
    }

    /**
     * Deletes an item from the pantry.
     */
    fun deletePantryItem(id: Int) {
        viewModelScope.launch {
            dao.deleteFromPantry(id)
        }
    }

    /**
     * Adds a new item to the shopping list.
     */
    fun addShoppingItem(name: String) {
        viewModelScope.launch {
            dao.addToShoppingList(ShoppingListItem(Name = name))
        }
    }

    /**
     * Toggles the checked state of a shopping item.
     */
    fun toggleShoppingItem(item: ShoppingListItem) {
        viewModelScope.launch {
            dao.updateShoppingItem(item.copy(isChecked = !item.isChecked))
        }
    }

    /**
     * Deletes a shopping item.
     */
    fun deleteShoppingItem(item: ShoppingListItem) {
        viewModelScope.launch {
            dao.deleteShoppingItem(item)
        }
    }

    /**
     * Transfers all checked shopping items to the pantry and removes them from the list.
     */
    fun checkoutCheckedItems() {
        viewModelScope.launch {
            val checkedItems = dao.getCheckedShoppingItems()
            checkedItems.forEach { item ->
                dao.addToPantry(PantryItem(Name = item.Name))
            }
            dao.deleteCheckedShoppingItems()
        }
    }
}
