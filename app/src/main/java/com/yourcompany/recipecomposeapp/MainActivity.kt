package com.yourcompany.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yourcompany.recipecomposeapp.core.network.NetworkConfig
import com.yourcompany.recipecomposeapp.core.network.api.RecipesApiService
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { _ ->
            deepLinkIntent = intent
        }
        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }

        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        lifecycleScope.launch {
            val contentType = "application/json".toMediaType()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(NetworkConfig.BASE_URL)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
            val service: RecipesApiService = retrofit.create(RecipesApiService::class.java)


            try {
                val categories = service.getCategories()

                Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                Log.i("!!!", "Количество категорий: ${categories.size}")

                categories.forEach { category ->
                    try {
                        val recipes = service.getRecipesByCategory(category.id)

                        Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                        Log.i("!!!", "Категория: ${category.title}")
                        Log.i("!!!", "Рецептов в категории: ${recipes.size}")
                    } catch (e: Exception) {
                        Log.i("!!!", "${e.message}")
                    }
                }
            } catch (e: Exception) {
                Log.i("!!!", "${e.message}")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}