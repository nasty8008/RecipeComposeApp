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
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)

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

        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()

            Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
            Log.i("!!!", "responseCode: ${connection.responseCode}")
            Log.i("!!!", "responseMessage: ${connection.responseMessage}")

            val responseText = connection.getInputStream().bufferedReader().readText()
            val json = Json
            val categories: List<CategoryDto> = json.decodeFromString(responseText)

            Log.i("!!!", "Количество категорий: ${categories.size}")
            categories.forEach {
                Log.i("!!!", "Категория: ${it.title}")
            }
        }
        thread.start()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}