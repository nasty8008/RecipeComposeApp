package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoritesScreen
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Preview(showBackground = true)
@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedCategoryTitle by remember { mutableStateOf<String?>(null) }
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
                    },
                )
            }
        ) { paddingValues ->
            when (currentScreen) {
                ScreenId.CATEGORIES -> CategoriesScreen(
                    modifier = Modifier.padding(paddingValues),
                    onCategoryClick = { category ->
                        selectedCategoryId = category.id
                        selectedCategoryTitle = category.title
                        currentScreen = ScreenId.RECIPES
                    }
                )
                ScreenId.FAVORITES -> FavoritesScreen(
                    modifier = Modifier.padding(paddingValues)
                )
                ScreenId.RECIPES -> RecipesScreen(
                    modifier = Modifier.padding(paddingValues),
                    categoryId = selectedCategoryId ?: error("Category ID is required"),
                    categoryTitle = selectedCategoryTitle ?: error("Category title is required"),
                )
            }
        }
    }
}