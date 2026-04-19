/**
 * Type converters for Room database to handle complex types like Lists.
 */
package com.example.cookbook.data.local

import androidx.room.TypeConverter

class Converters {
    /**
     * Converts a list of strings into a single delimited string for storage.
     */
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(";;")

    /**
     * Converts a delimited string back into a list of strings.
     */
    @TypeConverter
    fun toList(data: String): List<String> = data.split(";;")
}
