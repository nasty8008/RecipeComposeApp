package com.yourcompany.recipecomposeapp.features.details.presentation.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    fun loadRecipe(recipe: RecipeUiModel) {
        _uiState.update {
            it.copy(
                recipe = recipe,
                currentPortions = recipe.servings
            )
        }

        viewModelScope.launch {
            favoriteManager.getFavoriteIdsFlow()
                .map { ids -> ids.contains(recipe.id.toString()) }
                .collect { isFavorite ->
                    _uiState.update {
                        it.copy(isFavorite = isFavorite)
                    }
                }
        }
    }

    fun toggleFavorite() {
        val recipeId = _uiState.value.recipe?.id ?: return

        viewModelScope.launch {
            val isFavorite = _uiState.value.isFavorite

            if (isFavorite) {
                favoriteManager.removeFavorite(recipeId)
            } else {
                favoriteManager.addFavorite(recipeId)
            }
        }
    }

    fun updatePortions(newPortions: Int) {
        _uiState.update {
            it.copy(currentPortions = newPortions)
        }
    }
}