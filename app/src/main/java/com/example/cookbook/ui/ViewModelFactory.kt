/**
 * Factory for creating [CookBookViewModel] instances.
 * Handles the injection of the [CookBookDao] dependency.
 */
package com.example.cookbook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cookbook.data.local.dao.CookBookDao

class CookBookViewModelFactory(private val dao: CookBookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CookBookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CookBookViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}