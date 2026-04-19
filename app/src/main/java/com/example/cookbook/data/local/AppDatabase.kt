/**
 * The Room database definition for the CookBook application.
 * Manages storage for recipes, shopping lists, and pantry items.
 */
package com.example.cookbook.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.cookbook.data.local.dao.CookBookDao
import com.example.cookbook.data.local.entities.PantryItem
import com.example.cookbook.data.local.entities.Recipe
import com.example.cookbook.data.local.entities.ShoppingListItem

@Database(
    entities = [Recipe::class, ShoppingListItem::class, PantryItem::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the DAO for database operations.
     */
    abstract fun cookbookDao(): CookBookDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        /**
         * Singleton method to provide the database instance.
         */
        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cookbook_database"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}