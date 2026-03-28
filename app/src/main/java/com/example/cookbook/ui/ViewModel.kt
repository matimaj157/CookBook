package com.example.cookbook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import com.example.cookbook.data.local.dao.CookBookDao
import com.example.cookbook.data.local.entities.PantryItem
import com.example.cookbook.data.local.entities.Recipe
import com.example.cookbook.data.local.entities.ShoppingListItem

class CookBookViewModel(private val dao: CookBookDao) : ViewModel() {
    val recipes: Flow<List<Recipe>> = dao.getAllRecipes()
    val shoppingList: Flow<List<ShoppingListItem>> = dao.getShoppingList()
    val pantryItems: Flow<List<PantryItem>> = dao.getPantryItems()

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
