package com.yourcompany.recipecomposeapp.features.favorites.presentation.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.data.repository.getRecipeById
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            favoriteManager.getFavoriteIdsFlow()
                .map { ids ->
                    ids.mapNotNull { id ->
                        id.toIntOrNull()?.let { getRecipeById(it) }
                    }
                }
                .collect { recipes ->
                    _uiState.update {
                        it.copy(recipes = recipes.map { recipe -> recipe.toUiModel() })
                    }
                }
        }
    }
}