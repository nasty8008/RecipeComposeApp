package com.yourcompany.recipecomposeapp.ui.categories

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
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun CategoryItem(onClick: () -> Unit = {}, imageUrl: String, title: String, description: String) {
    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.CardShadow
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.CardHeight)
            .padding(Dimens.CardPadding)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_error)
                    .build(),
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
            onClick = {},
            imageUrl = getCategories()[0].toUiModel().imageUrl,
            title = getCategories()[0].toUiModel().title,
            description = getCategories()[0].toUiModel().description
        )
    }
}