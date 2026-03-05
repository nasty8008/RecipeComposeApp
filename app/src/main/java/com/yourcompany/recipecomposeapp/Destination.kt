package com.yourcompany.recipecomposeapp

import com.yourcompany.recipecomposeapp.Constants.PARAM_RECIPE_ID

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }
    object RecipeDetails : Destination("recipe/{$PARAM_RECIPE_ID}") {
        fun createRoute(recipeId: Int) = "recipe/$recipeId"
    }
}