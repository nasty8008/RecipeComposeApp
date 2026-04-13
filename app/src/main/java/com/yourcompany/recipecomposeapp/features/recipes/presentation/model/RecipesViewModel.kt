package com.yourcompany.recipecomposeapp.features.recipes.presentation.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourcompany.recipecomposeapp.core.utils.Constants
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

class RecipesViewModel(
    state: SavedStateHandle,
    repository: RecipesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    private val categoryId: Int = state[Constants.PARAM_CATEGORY_ID] ?: 0
    private val categoryTitle: String = state[Constants.PARAM_CATEGORY_TITLE] ?: ""
    private val categoryImageUrl: String = URLDecoder.decode(state[Constants.PARAM_CATEGORY_IMAGE_URL] ?: "", "UTF-8")

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
                val recipes = repository.getRecipesByCategory(categoryId).map { it.toUiModel() }

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