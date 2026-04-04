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
    val searchQuery: StateFlow<String> = _searchQuery

    val recipes: StateFlow<List<Recipe>> = dao.getAllRecipes()
        .combine(_searchQuery) { recipes, query ->
            if (query.isBlank()) {
                recipes
            } else {
                recipes.filter { it.name.contains(query, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shoppingList: Flow<List<ShoppingListItem>> = dao.getShoppingList()
    val pantryItems: Flow<List<PantryItem>> = dao.getPantryItems()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addRecipe(name: String, desc: String, ingredients: List<String>, uri: String?) {
        viewModelScope.launch {
            dao.insertRecipe(Recipe(name = name, description = desc, ingredients = ingredients, mediaUri = uri))
        }
    }

    fun addPantryItem(name: String) {
        viewModelScope.launch {
            dao.addToPantry(PantryItem(Name = name))
        }
    }

    fun deletePantryItem(id: Int) {
        viewModelScope.launch {
            dao.deleteFromPantry(id)
        }
    }

    fun addShoppingItem(name: String) {
        viewModelScope.launch {
            dao.addToShoppingList(ShoppingListItem(Name = name))
        }
    }

    fun toggleShoppingItem(item: ShoppingListItem) {
        viewModelScope.launch {
            // Tworzymy kopię obiektu z odwróconą wartością isChecked i aktualizujemy w bazie
            dao.updateShoppingItem(item.copy(isChecked = !item.isChecked))
        }
    }

    fun deleteShoppingItem(item: ShoppingListItem) {
        viewModelScope.launch {
            dao.deleteShoppingItem(item)
        }
    }
}
