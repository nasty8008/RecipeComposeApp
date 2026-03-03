package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.data.repository.getRecipesByCategoryId
import com.yourcompany.recipecomposeapp.ui.recipes.model.IngredientUiModel
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel

@Composable
fun IngredientItem(
    ingredient: IngredientUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ingredient.name.uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = modifier.weight(1f),
            maxLines = 2,
        )
        Text(
            text = "${ingredient.amount.toInt()} ${ingredient.unitOfMeasure}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientItemPreview() {
    IngredientItem(getRecipesByCategoryId(0)[0].ingredients[0].toUiModel())
}