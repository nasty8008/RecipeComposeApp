package com.yourcompany.recipecomposeapp.features.details.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.core.ui.theme.Dimens
import com.yourcompany.recipecomposeapp.features.details.presentation.model.RecipeDetailsViewModel
import com.yourcompany.recipecomposeapp.features.recipes.ui.IngredientItem

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    onShareClick: () -> Unit,
    viewModel: RecipeDetailsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val recipe = state.recipe

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        recipe?.let {
            RecipeHeader(
                title = it.title,
                image = it.imageUrl,
                onShareClick = onShareClick,
                isFavorite = state.isFavorite,
                onFavoriteToggle = { viewModel.toggleFavorite() },
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
                        text = "Порции: ${state.currentPortions}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    PortionsSlider(
                        state.currentPortions,
                        onPortionsChange = viewModel::updatePortions
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
                        state.scaledIngredients.forEachIndexed { index, ingredient ->
                            IngredientItem(ingredient)
                            if (index != state.scaledIngredients.lastIndex) {
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
                        it.method.forEachIndexed { index, step ->
                            Text(
                                text = "${index + 1}. $step",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSecondary,
                            )
                            if (index != it.method.lastIndex) {
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
}
