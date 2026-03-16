package com.yourcompany.recipecomposeapp.core.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.yourcompany.recipecomposeapp.core.utils.Constants.KEY_FAVORITES
import com.yourcompany.recipecomposeapp.core.utils.Constants.PREF_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREF_NAME,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context = context,
                sharedPreferencesName = "FavoriteRecipes"
            )
        )
    }
)

object PreferencesKeys {
    val FAVORITE_RECIPE_IDS = stringSetPreferencesKey(KEY_FAVORITES)
}