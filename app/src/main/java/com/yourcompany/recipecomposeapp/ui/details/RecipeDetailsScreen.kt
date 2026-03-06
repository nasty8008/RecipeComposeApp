package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.yourcompany.recipecomposeapp.ui.recipes.IngredientItem
import com.yourcompany.recipecomposeapp.ui.recipes.model.IngredientUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
    onShareClick: () -> Unit
) {
    var currentPortions by rememberSaveable { mutableIntStateOf(recipe.servings) }
    var isFavorite by rememberSaveable { mutableStateOf(false) }

    val scaledIngredients: List<IngredientUiModel> = remember(recipe.ingredients, currentPortions) {
        val multiplier = currentPortions.toDouble() / recipe.servings.toDouble()

        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                amount = ingredient.amount * multiplier
            )
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RecipeHeader(
            title = recipe.title,
            image = recipe.imageUrl,
            onShareClick = onShareClick,
            isFavorite = isFavorite,
            onFavoriteToggle = {
                isFavorite = !isFavorite
            },
            showFavoriteButton = true
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.ColumnContentPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.ColumnContentPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.PortionSectionSpacer)
            ) {
                Text(
                    text = "ИНГРЕДИЕНТЫ",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Порции: $currentPortions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                PortionsSlider(
                    currentPortions,
                    onPortionsChange = { newValue ->
                        currentPortions = newValue
                    }
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.ColumnContentPadding)
                ) {
                    scaledIngredients.forEachIndexed { index, ingredient ->
                        IngredientItem(ingredient)
                        if (index != scaledIngredients.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = Dimens.HorizontalDividerModifier),
                                thickness = Dimens.HorizontalDividerThickness,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
            Text(
                text = "СПОСОБ ПРИГОТОВЛЕНИЯ",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.ColumnContentPadding)
                ) {
                    recipe.method.forEachIndexed { index, step ->
                        Text(
                            text = "${index + 1}. $step",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                        if (index != recipe.method.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = Dimens.HorizontalDividerModifier),
                                thickness = Dimens.HorizontalDividerThickness,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}