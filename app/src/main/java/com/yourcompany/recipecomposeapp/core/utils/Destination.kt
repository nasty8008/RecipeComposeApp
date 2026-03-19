package com.yourcompany.recipecomposeapp.core.utils

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination(
        "recipes/{${Constants.PARAM_CATEGORY_ID}}/{${Constants.PARAM_CATEGORY_TITLE}}/{${Constants.PARAM_CATEGORY_IMAGE_URL}}"
    ) {
        fun createRoute(
            categoryId: Int,
            categoryTitle: String,
            categoryImageUrl: String
        ): String {
            val encodedTitle = java.net.URLEncoder.encode(categoryTitle, "UTF-8")
            val encodedImage = java.net.URLEncoder.encode(categoryImageUrl, "UTF-8")

            return "recipes/$categoryId/$encodedTitle/$encodedImage"
        }
    }
    object RecipeDetails : Destination("recipe/{${Constants.PARAM_RECIPE_ID}}") {
        fun createRoute(
            recipeId: Int,
            ) = "recipe/$recipeId"
    }
}