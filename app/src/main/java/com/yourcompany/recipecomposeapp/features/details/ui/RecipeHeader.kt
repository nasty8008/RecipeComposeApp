package com.yourcompany.recipecomposeapp.features.details.ui

import androidx.compose.runtime.Composable
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader

@Composable
fun RecipeHeader(
    title: String,
    image: Any,
    onShareClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    isFavorite: Boolean,
    showFavoriteButton: Boolean
) {
    ScreenHeader(
        title = title,
        image = image,
        showShareButton = true,
        onShareClick = onShareClick,
        onFavoriteToggle = onFavoriteToggle,
        isFavorite = isFavorite,
        showFavoriteButton  = showFavoriteButton
    )
}