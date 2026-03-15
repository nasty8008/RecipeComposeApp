package com.yourcompany.recipecomposeapp.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.getRecipeById
import com.yourcompany.recipecomposeapp.data.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.ui.recipes.RecipeItem
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.Dimens
import kotlinx.coroutines.flow.map


@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favoriteManager: FavoriteDataStoreManager,
    onRecipeClick: (Int, RecipeUiModel) -> Unit
) {
    val favoriteRecipes by remember { favoriteManager.getFavoriteIdsFlow() }
        .map { ids ->
            ids.mapNotNull { id ->
                id.toIntOrNull()?.let { getRecipeById(it) }
            }
        }
        .collectAsState(initial = emptyList())

    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            ScreenId.FAVORITES.displayName,
            R.drawable.bcg_favorites
        )

        if (favoriteRecipes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Вы еще не добавили ни одного рецепта в избранное",
                    modifier = Modifier.padding(Dimens.ColumnContentPadding),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(Dimens.CardPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.CardRecipeSpacing)
            ) {
                items(favoriteRecipes, key = { it.id }) { recipe ->
                    val recipeUiModel = recipe.toUiModel()
                    RecipeItem(
                        onClick = { onRecipeClick(it.id, recipeUiModel) },
                        recipe = recipeUiModel
                    )
                }
            }
        }
    }
}