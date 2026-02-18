package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.ui.categories.ScreenHeader

@Preview(showBackground = true)
@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    currentScreen,
                    onCategoriesClick = {
                        currentScreen = ScreenId.CATEGORIES
                    },
                    onFavoriteClick = {
                        currentScreen = ScreenId.FAVORITES
                    }
                )
            }
        ) { paddingValues ->
            when (currentScreen) {
                ScreenId.CATEGORIES -> CategoriesScreen(
                    modifier = Modifier.padding(paddingValues)
                )
                ScreenId.FAVORITES -> FavoritesScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            ScreenId.CATEGORIES.displayName,
            R.drawable.categories_header
        )
        LazyColumn(
            modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(10) { index ->
                Text(
                    text = "Категория ${index + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = ScreenId.FAVORITES.displayName,
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}