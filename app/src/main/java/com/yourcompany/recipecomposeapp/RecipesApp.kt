package com.yourcompany.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.recipecomposeapp.Constants.DEEP_LINK_SCHEME
import com.yourcompany.recipecomposeapp.Constants.KEY_RECIPE_OBJECT
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.data.repository.getRecipeById
import com.yourcompany.recipecomposeapp.data.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.data.utils.shareRecipe
import com.yourcompany.recipecomposeapp.ui.categories.CategoriesScreen
import com.yourcompany.recipecomposeapp.ui.categories.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.ui.favorites.FavoritesScreen
import com.yourcompany.recipecomposeapp.ui.navigation.BottomNavigation
import com.yourcompany.recipecomposeapp.ui.recipes.RecipesScreen
import com.yourcompany.recipecomposeapp.ui.recipes.model.toUiModel
import com.yourcompany.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RecipesApp(deepLinkIntent: Intent?) {
    val navController = rememberNavController()

    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                DEEP_LINK_SCHEME ->
                    if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                "https", "http" ->
                    if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                else -> null
            }

            if (recipeId != null) {
                delay(100)
                navController.navigate(Destination.RecipeDetails.createRoute(recipeId))
            }
        }
    }

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories)
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites)
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
                    route = Destination.RecipeDetails.route,
                    arguments = listOf(
                        navArgument(Constants.PARAM_RECIPE_ID) {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()

                    val recipeId = backStackEntry.arguments?.getInt(Constants.PARAM_RECIPE_ID) ?: 0
                    val recipe = getRecipeById(recipeId)

                    val favoriteManager = remember { FavoriteDataStoreManager(context) }
                    val isFavorite by favoriteManager
                        .isFavoriteFlow(recipe?.id ?: 0)
                        .collectAsState(initial = false)

                    recipe?.let {
                        RecipeDetailsScreen(
                            recipe = it.toUiModel(),
                            modifier = Modifier.padding(paddingValues),
                            isFavorite = isFavorite,
                            onFavoriteToggle = {
                                coroutineScope.launch {
                                    if (isFavorite) {
                                        favoriteManager.removeFavorite(recipeId)
                                    } else {
                                        favoriteManager.addFavorite(recipeId)
                                    }
                                }
                            },
                            onShareClick = {
                                shareRecipe(
                                    context = context,
                                    recipeId = it.id,
                                    recipeTitle = it.title
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipeComposeAppTheme {
        RecipesApp(deepLinkIntent = null)
    }
}