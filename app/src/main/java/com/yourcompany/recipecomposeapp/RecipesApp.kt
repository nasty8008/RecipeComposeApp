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
import com.yourcompany.recipecomposeapp.Constants.KEY_RECIPE_OBJECT
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoritesScreen
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.recipes.model.RecipeUiModel
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
                startDestination = Destination.Categories.route
            ) {
                composable(route = Destination.Categories.route) {
                    CategoriesScreen(
                        modifier = Modifier.padding(paddingValues),
                        onCategoryClick = { categoryId ->
                            navController.navigate(Destination.Recipes.createRoute(categoryId))
                        }
                    )
                }
                composable(route = Destination.Favorites.route) {
                    FavoritesScreen(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                    val category = getCategories()
                        .map { it.toUiModel() }
                        .find { it.id == categoryId }
                    if (category !== null) {
                        RecipesScreen(
                            onRecipeClick = { id, recipe ->
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set(KEY_RECIPE_OBJECT, recipe)
                                navController.navigate(Destination.RecipeDetails.createRoute(id))
                            },
                            modifier = Modifier.padding(paddingValues),
                            categoryId = category.id,
                            categoryTitle = category.title,
                            categoryImage = category.imageUrl,
                        )
                    } else {
                        Text("Категория не найдена")
                    }
                }
                composable(
                    route = Destination.RecipeDetails.route
                ) {
                    val recipe = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<RecipeUiModel>(KEY_RECIPE_OBJECT)

                    if (recipe != null) {
                        RecipeDetailsScreen(
                            recipe = recipe,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}