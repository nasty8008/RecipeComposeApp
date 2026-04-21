package com.yourcompany.recipecomposeapp.features.categories.ui

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
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import com.yourcompany.recipecomposeapp.core.ui.RecipeImage
import com.yourcompany.recipecomposeapp.core.ui.theme.Dimens

@Composable
fun CategoryItem(
    onClick: (CategoryUiModel) -> Unit,
    category: CategoryUiModel,
    imageUrl: String,
    title: String,
    description: String
) {
    Card(
        onClick = { onClick(category) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardShadow
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.CardHeightCategory)
            .padding(Dimens.CardPadding)
    ) {
        Column {
            RecipeImage(
                modifier = Modifier.weight(1f),
                imageUrl = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(Dimens.CardTextPadding)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(
                            bottom = Dimens.CardTextPadding
                        )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MaterialTheme {
        CategoryItem(
            onClick = { },
            category = getCategories()[0].toUiModel(),
            imageUrl = getCategories()[0].toUiModel().imageUrl,
            title = getCategories()[0].toUiModel().title,
            description = getCategories()[0].toUiModel().description
        )
    }
}