package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.getRecipesByCategoryId
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
    categoryId: Int?,
    categoryTitle: String,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }
    LaunchedEffect(categoryId) {
        categoryId?.let {
            recipes = getRecipesByCategoryId(it).map { dto -> dto.toUiModel() }
        }
    }
    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            title = categoryTitle,
            imageId = R.drawable.bcg_recipes_list
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(Dimens.CardPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.CardRecipeSpacing)
        ) {
            items(recipes, key = {it.id}) { recipe ->
                RecipeItem(
                    onClick = { onRecipeClick(it.id, recipe) },
                    recipe = recipe
                )
            }
        }
    }
}