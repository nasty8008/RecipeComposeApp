package com.yourcompany.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.core.ui.theme.Dimens
import com.yourcompany.recipecomposeapp.features.categories.presentation.CategoriesViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit,
    viewModel: CategoriesViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            ScreenId.CATEGORIES.displayName,
            R.drawable.bcg_categories,
        )
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = state.error ?: "Error",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.CardPadding),
                    verticalArrangement = Arrangement.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    items(state.categories) { category ->
                        CategoryItem(
                            onClick = { onCategoryClick(category.id, category.title, category.imageUrl) },
                            category = category,
                            imageUrl = category.imageUrl,
                            title = category.title,
                            description = category.description
                        )
                    }
                }
            }
        }
    }
}