package com.yourcompany.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.core.ui.theme.Dimens
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesViewModel

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            title = state.categoryTitle,
            image = state.categoryImageUrl,
        )
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.error ?: "Error",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            state.isEmpty -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Нет рецептов",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.CardPadding),
                    verticalArrangement = Arrangement.spacedBy(Dimens.CardRecipeSpacing)
                ) {
                    items(state.recipes, key = { it.id }) { recipe ->
                        RecipeItem(
                            onClick = { onRecipeClick(recipe.id, recipe) },
                            recipe = recipe
                        )
                    }
                }
            }
        }
    }
}