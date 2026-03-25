package com.yourcompany.recipecomposeapp.features.details.presentation.model

import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val servings: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val scaledIngredients: List<IngredientUiModel>?
        get() = recipe?.ingredients?.map { ingredient ->
            ingredient.copy(
                amount = ingredient.amount * servings
            )
        }
}