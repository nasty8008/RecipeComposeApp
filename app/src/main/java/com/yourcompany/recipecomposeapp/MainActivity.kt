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
import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val client = OkHttpClient()
    private val json = Json

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

        thread {
            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                    Log.i("!!!", "responseCode: ${response.code}")
                    Log.i("!!!", "responseMessage: ${response.message}")

                    val responseText: String = response.body.string()
                    Log.i("!!!", responseText)

                    val categories: List<CategoryDto> = json.decodeFromString(responseText)
                    Log.i("!!!", "Количество категорий: ${categories.size}")

                    categories.forEach { category ->
                        threadPool.execute {
                            val request = Request.Builder()
                                .url("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                                .build()
                            try {
                                client.newCall(request).execute().use { response ->
                                    val responseText: String = response.body.string()
                                    val recipes: List<RecipeDto> = json.decodeFromString(responseText)
                                    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                                    Log.i("!!!", responseText)
                                    Log.i("!!!", "Категория: ${category.title}")
                                    Log.i("!!!", "Рецептов в категории: ${recipes.size}")
                                }
                            } catch (e: Exception) {
                                Log.i("!!!", "${e.message}")
                            }
                        }
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

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }
}