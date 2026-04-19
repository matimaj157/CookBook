/**
 * Entity representing an item in the shopping list.
 */
package com.example.cookbook.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val Name: String,
    val isChecked: Boolean = false
)
