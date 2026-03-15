package com.yourcompany.recipecomposeapp.core.utils

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }
    object RecipeDetails : Destination("recipe/{${Constants.PARAM_RECIPE_ID}}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
}