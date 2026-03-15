package com.yourcompany.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.yourcompany.recipecomposeapp.core.ui.theme.Dimens

@Composable
fun RecipeItem(
    onClick: (RecipeUiModel) -> Unit,
    recipe: RecipeUiModel,
) {
    Card(
        onClick = { onClick(recipe) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardShadow
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.CardHeightRecipe)
            .padding(Dimens.CardPadding)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(Dimens.CardTextPadding)
            )
        }
    }
}