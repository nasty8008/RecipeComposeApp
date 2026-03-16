package com.yourcompany.recipecomposeapp.core.utils

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val KEY_RECIPE_OBJECT = "recipe"
    const val PARAM_RECIPE_ID = "recipeId"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
    const val PREF_NAME = "recipe_app_prefs"
    const val KEY_FAVORITES = "favorite_recipe_ids"

    fun createRecipeDeepLink(recipeId: Int): String {
        return "$DEEP_LINK_BASE_URL/recipe/$recipeId"
    }
}