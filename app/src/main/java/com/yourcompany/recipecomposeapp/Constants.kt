package com.yourcompany.recipecomposeapp

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val PARAM_RECIPE_ID = 0
    const val KEY_RECIPE_OBJECT = "recipe/{${PARAM_RECIPE_ID}}"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
}

fun createRecipeDeepLink(recipeId: Int) {

}