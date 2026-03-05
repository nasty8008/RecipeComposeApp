package com.yourcompany.recipecomposeapp.ui.details

import androidx.compose.runtime.Composable
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader

@Composable
fun RecipeHeader(
    title: String,
    image: Any,
    onShareClick: () -> Unit
) {
    ScreenHeader(
        title = title,
        image = image,
        showShareButton = true,
        onShareClick = onShareClick
    )
}