package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import com.yourcompany.recipecomposeapp.core.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int) -> Unit
) {
    val categories: List<CategoryUiModel> = getCategories().map { it.toUiModel() }
    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            ScreenId.CATEGORIES.displayName,
            R.drawable.bcg_categories,
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(Dimens.CardPadding),
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(categories) { category ->
                CategoryItem(
                    onClick = { onCategoryClick(category.id) },
                    category = category,
                    imageUrl = category.imageUrl,
                    title = category.title,
                    description = category.description
                )
            }
        }
    }
}