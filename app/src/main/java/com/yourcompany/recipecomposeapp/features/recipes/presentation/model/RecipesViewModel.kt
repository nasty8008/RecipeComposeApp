package com.yourcompany.recipecomposeapp.features.recipes.presentation.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.data.repository.getRecipesByCategoryId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(state: SavedStateHandle) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    private val categoryId: Int = state["categoryId"] ?: 0
    private val categoryTitle: String = state["categoryTitle"] ?: ""
    private val categoryImageUrl: String = state["categoryImageUrl"] ?: ""

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    categoryTitle = categoryTitle,
                    categoryImageUrl = categoryImageUrl
                )
            }

            try {
                val recipes = getRecipesByCategoryId(categoryId).map { it.toUiModel() }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipes = recipes
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error"
                    )
                }
            }
        }
    }
}