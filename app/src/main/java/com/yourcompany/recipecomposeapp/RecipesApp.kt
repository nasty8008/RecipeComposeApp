package com.yourcompany.recipecomposeapp

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yourcompany.recipecomposeapp.core.network.NetworkConfig
import com.yourcompany.recipecomposeapp.core.network.api.RecipesApiService
import com.yourcompany.recipecomposeapp.core.ui.BottomNavigation
import com.yourcompany.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.yourcompany.recipecomposeapp.core.utils.Constants
import com.yourcompany.recipecomposeapp.core.utils.Constants.DEEP_LINK_SCHEME
import com.yourcompany.recipecomposeapp.core.utils.Destination
import com.yourcompany.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.yourcompany.recipecomposeapp.core.utils.shareRecipe
import com.yourcompany.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.yourcompany.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.yourcompany.recipecomposeapp.features.details.presentation.model.RecipeDetailsViewModel
import com.yourcompany.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.yourcompany.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.yourcompany.recipecomposeapp.features.recipes.presentation.model.RecipesViewModel
import com.yourcompany.recipecomposeapp.features.recipes.ui.RecipesScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
@Composable
fun RecipesApp(deepLinkIntent: Intent?) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val favoriteManager = remember { FavoriteDataStoreManager(context) }
    val favoriteCount by favoriteManager
        .getFavoriteCountFlow()
        .collectAsState(initial = 0)

    val repository = remember {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        val contentType = "application/json".toMediaType()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)  // 1. Установка соединения
            .readTimeout(30, TimeUnit.SECONDS)     // 2. Чтение ответа от сервера
            .writeTimeout(30, TimeUnit.SECONDS)    // 3. Отправка данных на сервер
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService: RecipesApiService =
            retrofit.create(RecipesApiService::class.java)

        RecipesRepositoryImpl(apiService)
    }

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
                        },
                        repository = repository
                    )
                }
                composable(route = Destination.Favorites.route) {
                    FavoritesScreen(
                        modifier = Modifier.padding(paddingValues),
                        repository = repository,
                        onRecipeClick = { id, recipe ->
//                            navController.currentBackStackEntry
//                                ?.savedStateHandle
//                                ?.set(KEY_RECIPE_OBJECT, recipe)
                            navController.navigate(Destination.RecipeDetails.createRoute(id))
                        }
                    )
                }
                composable(
                    route = Destination.Recipes.route,
                    arguments = listOf(
                        navArgument(Constants.PARAM_CATEGORY_ID) { type = NavType.IntType },
                        navArgument(Constants.PARAM_CATEGORY_TITLE) { type = NavType.StringType },
                        navArgument(Constants.PARAM_CATEGORY_IMAGE_URL) {
                            type = NavType.StringType
                        }
                    )
                ) { backStackEntry ->
                    val viewModel = remember(backStackEntry) {
                        RecipesViewModel(
                            backStackEntry.savedStateHandle,
                            repository
                        )
                    }

                    RecipesScreen(
                        onRecipeClick = { id, recipe ->
//                            navController.currentBackStackEntry
//                                ?.savedStateHandle
//                                ?.set(KEY_RECIPE_OBJECT, recipe)
                            navController.navigate(Destination.RecipeDetails.createRoute(id))
                        },
                        modifier = Modifier.padding(paddingValues),
                        viewModel = viewModel
                    )
                }
                composable(
                    route = Destination.RecipeDetails.route,
                    arguments = listOf(
                        navArgument(Constants.PARAM_RECIPE_ID) {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->
                    val viewModel = remember(backStackEntry) {
                        RecipeDetailsViewModel(
                            context.applicationContext as Application,
                            backStackEntry.savedStateHandle,
                            repository
                        )
                    }

                    val recipe = viewModel.uiState.collectAsState().value.recipe

                    recipe?.let {
                        RecipeDetailsScreen(
                            modifier = Modifier.padding(paddingValues),
                            onShareClick = {
                                shareRecipe(
                                    context = context,
                                    recipeId = it.id,
                                    recipeTitle = it.title
                                )
                            },
                            viewModel = viewModel
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