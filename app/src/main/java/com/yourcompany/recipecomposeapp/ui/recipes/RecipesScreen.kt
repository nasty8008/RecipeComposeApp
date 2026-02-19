package com.yourcompany.recipecomposeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            title = ScreenId.RECIPES.displayName,
            imageId = R.drawable.bcg_recipes_list
        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(Dimens.HeaderTextPadding),
            text = "Скоро здесь будут рецепты",
            textAlign = TextAlign.Center
        )
    }
}