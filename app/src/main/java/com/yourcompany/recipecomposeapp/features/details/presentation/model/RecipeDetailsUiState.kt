package com.yourcompany.recipecomposeapp.features.details.presentation.model

import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val currentPortions: Int = 1,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val scaledIngredients: List<IngredientUiModel>
        get() {
            val recipe = recipe ?: return emptyList()
            val multiplier = currentPortions.toDouble() / recipe.servings.toDouble()

            return recipe.ingredients.map { ingredient ->
                ingredient.copy(
                    amount = ingredient.amount * multiplier
                )
            }
        }
}