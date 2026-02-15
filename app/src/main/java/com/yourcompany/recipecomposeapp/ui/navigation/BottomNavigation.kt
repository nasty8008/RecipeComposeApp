package com.yourcompany.recipecomposeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.yourcompany.recipecomposeapp.ScreenId

@Composable
fun BottomNavigation(
    currentScreen: ScreenId,
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentScreen == ScreenId.CATEGORIES,
            onClick = onCategoriesClick,
            icon = { Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = null) },
            label = { Text(ScreenId.CATEGORIES.displayName) }
        )

        NavigationBarItem(
            selected = currentScreen == ScreenId.FAVORITES,
            onClick = onFavoriteClick,
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) },
            label = { Text(ScreenId.FAVORITES.displayName) }
        )
    }
}