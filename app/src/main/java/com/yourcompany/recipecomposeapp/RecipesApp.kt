package com.yourcompany.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.favorites.FavoritesScreen
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Preview(showBackground = true)
@Composable
fun RecipesApp() {
    val navController = rememberNavController()

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate("categories")
                    },
                    onFavoriteClick = {
                        navController.navigate("favorites")
                    },
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "categories"
            ) {
                composable(route = "categories") {
                    CategoriesScreen(
                        modifier = Modifier.padding(paddingValues),
                        onCategoryClick = { categoryId ->
                            navController.navigate("recipes/$categoryId")
                        }
                    )
                }
                composable(route = "favorites") {
                    FavoritesScreen(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                composable(
                    route = "recipes/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                    val category = getCategories()
                        .map { it.toUiModel() }
                        .find { it.id == categoryId }

                    if (category !== null) {
                        RecipesScreen(
                            onRecipeClick = {
                                navController.navigate("")
                            },
                            modifier = Modifier.padding(paddingValues),
                            categoryId = category.id,
                            categoryTitle = category.title,
                        )
                    } else {
                        Text("Категория не найдена")
                    }
                }
            }
        }
    }
}