package com.yourcompany.recipecomposeapp.features.details.presentation.model

import androidx.lifecycle.SavedStateHandle
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.core.utils.Constants
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.data.repository.getRecipeById
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application, state: SavedStateHandle) : AndroidViewModel(application) {

    private val recipeId: Int = state[Constants.PARAM_RECIPE_ID] ?: 0

    private val favoriteManager = FavoriteDataStoreManager(application)
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRecipe(recipeId)
    }

//    fun loadRecipe(recipe: RecipeUiModel) {
//        _uiState.update {
//            it.copy(
//                recipe = recipe,
//                currentPortions = recipe.servings
//            )
//        }
//
//        viewModelScope.launch {
//            favoriteManager.getFavoriteIdsFlow()
//                .map { ids -> ids.contains(recipe.id.toString()) }
//                .collect { isFavorite ->
//                    _uiState.update {
//                        it.copy(isFavorite = isFavorite)
//                    }
//                }
//        }
//    }

    fun loadRecipe(recipeId: Int) {
        val recipe = getRecipeById(recipeId)?.toUiModel() ?: return
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