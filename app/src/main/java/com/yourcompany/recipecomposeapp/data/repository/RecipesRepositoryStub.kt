package com.yourcompany.recipecomposeapp.data.repository

import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.RecipeDto

private val categories = listOf(
    CategoryDto(
        id = 0,
        title = "Бургеры",
        description = "Рецепты всех популярных видов бургеров",
        imageUrl = "burger.png"
    ),
    CategoryDto(
        id = 1,
        title = "Дессерты",
        description = "Самые вкусные рецепты десертов специально для вас",
        imageUrl = "dessert.png"
    ),
    CategoryDto(
        id = 2,
        title = "Рыба",
        description = "Печеная, жареная, сушеная, любая рыба на твой вкус",
        imageUrl = "fish.png"
    ),
    CategoryDto(
        id = 3,
        title = "Пицца",
        description = "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
        imageUrl = "pizza.png"
    ),
    CategoryDto(
        id = 4,
        title = "Салаты",
        description = "Хрустящий калейдоскоп под соусом вдохновения",
        imageUrl = "salad.png"
    ),
    CategoryDto(
        id = 5,
        title = "Супы",
        description = "От классики до экзотики: мир в одной тарелке",
        imageUrl = "soup.png"
    ),
)

private val burgerRecipes = listOf<RecipeDto>()

fun getCategories(): List<CategoryDto> {
    return categories
}

fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> {
    return when (categoryId) {
        0 -> burgerRecipes
        else -> emptyList()
    }
}