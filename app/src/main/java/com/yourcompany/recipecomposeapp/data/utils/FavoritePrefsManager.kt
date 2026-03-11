package com.yourcompany.recipecomposeapp.data.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.yourcompany.recipecomposeapp.Constants.KEY_FAVORITES
import com.yourcompany.recipecomposeapp.Constants.PREF_NAME

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        return getAllFavorites().contains(recipeId.toString())
    }

    fun addToFavorites(recipeId: Int) {
        val favorites = getAllFavorites().toMutableSet()
        favorites.add(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(KEY_FAVORITES, favorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val favorites = getAllFavorites().toMutableSet()
        favorites.remove(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(KEY_FAVORITES, favorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        return sharedPreferences.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }
}