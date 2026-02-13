package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

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
            Text("Recipes App", modifier = Modifier.padding(paddingValues))
            when (currentScreen) {
                ScreenId.CATEGORIES -> CategoriesScreen()
                ScreenId.FAVORITES -> FavoritesScreen()
            }
        }
    }
}

@Composable
fun CategoriesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = ScreenId.CATEGORIES.displayName,
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FavoritesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = ScreenId.FAVORITES.displayName,
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}