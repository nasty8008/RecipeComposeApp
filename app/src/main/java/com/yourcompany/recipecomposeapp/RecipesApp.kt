package com.yourcompany.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.recipecomposeapp.core.utils.Constants.DEEP_LINK_SCHEME
import com.yourcompany.recipecomposeapp.core.utils.Constants.KEY_RECIPE_OBJECT
import com.yourcompany.recipecomposeapp.data.repository.getCategories
import com.yourcompany.recipecomposeapp.data.repository.getRecipeById
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.core.utils.shareRecipe
import com.yourcompany.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.yourcompany.recipecomposeapp.features.categories.presentation.model.toUiModel
import com.yourcompany.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.yourcompany.recipecomposeapp.core.ui.BottomNavigation
import com.yourcompany.recipecomposeapp.features.recipes.ui.RecipesScreen
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.core.utils.Constants
import com.yourcompany.recipecomposeapp.core.utils.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RecipesApp(deepLinkIntent: Intent?) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val favoriteManager = remember { FavoriteDataStoreManager(context) }
    val favoriteCount by favoriteManager
        .getFavoriteCountFlow()
        .collectAsState(initial = 0)

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
                    favoriteCount = favoriteCount,
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories.route)
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites.route)
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
                        onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                            navController.navigate(
                                Destination.Recipes.createRoute(
                                    categoryId,
                                    categoryTitle,
                                    categoryImageUrl
                                )
                            )
                        }
                    )
                }
                composable(route = Destination.Favorites.route) {
                    FavoritesScreen(
                        modifier = Modifier.padding(paddingValues),
                        favoriteManager = favoriteManager,
                        onRecipeClick = { id, recipe ->
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set(KEY_RECIPE_OBJECT, recipe)
                            navController.navigate(Destination.RecipeDetails.createRoute(id))
                        }
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
                    val recipeId = backStackEntry.arguments?.getInt(Constants.PARAM_RECIPE_ID) ?: 0
                    val recipe = getRecipeById(recipeId)

                    val isFavorite by favoriteManager
                        .isFavoriteFlow(recipeId)
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