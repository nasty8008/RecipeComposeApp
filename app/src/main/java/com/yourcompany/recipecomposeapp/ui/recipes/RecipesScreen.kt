package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader

@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            title = ScreenId.RECIPES.displayName,
            imageId = R.drawable.bcg_categories
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Скоро здесь будут рецепты",
            textAlign = TextAlign.Center
        )
    }
}