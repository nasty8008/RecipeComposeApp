package com.yourcompany.recipecomposeapp.data.repository

import com.yourcompany.recipecomposeapp.data.model.CategoryDto
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
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

private val burgerRecipes = listOf(
    RecipeDto(
        id = 0,
        title = "Классический чизбургер",
        ingredients = listOf(
            IngredientDto(quantity = 400.0, unitOfMeasure = "г", description = "Говяжий фарш"),
            IngredientDto(quantity = 4.0, unitOfMeasure = "ломтика", description = "Сыр чеддер"),
            IngredientDto(
                quantity = 4.0,
                unitOfMeasure = "штуки",
                description = "Булочки для бургера"
            ),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Лук репчатый"),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Помидор"),
            IngredientDto(quantity = 100.0, unitOfMeasure = "г", description = "Листья салата"),
            IngredientDto(quantity = 2.0, unitOfMeasure = "ст. ложки", description = "Кетчуп"),
            IngredientDto(quantity = 2.0, unitOfMeasure = "ст. ложки", description = "Горчица"),
            IngredientDto(quantity = 2.0, unitOfMeasure = "ст. ложки", description = "Майонез"),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Соленый огурец")
        ),
        method = listOf(
            "1. Разделите говяжий фарш на 4 равные части и сформируйте котлеты толщиной около 2 см. Сделайте пальцем небольшое углубление в центре каждой котлеты, чтобы они не деформировались при жарке.",
            "2. Посолите и поперчите котлеты с обеих сторон. Разогрейте сковороду или гриль и обжаривайте котлеты по 3-4 минуты с каждой стороны.",
            "3. За минуту до готовности положите на каждую котлету по ломтику сыра чеддер, чтобы он слегка расплавился.",
            "4. Разрежьте булочки пополам и подрумяньте их на сковороде или в тостере.",
            "5. Нарежьте лук кольцами, помидор - тонкими кружочками, соленый огурец - слайсами.",
            "6. Смешайте кетчуп, горчицу и майонез для соуса.",
            "7. На нижнюю половинку булочки нанесите соус, выложите лист салата, котлету с сыром, лук, помидор, соленый огурец.",
            "8. Накройте верхней половинкой булочки и подавайте сразу же."
        ),
        imageUrl = "burger-cheeseburger.png"
    ),
    RecipeDto(
        id = 1,
        title = "Веганский бургер",
        ingredients = listOf(
            IngredientDto(
                quantity = 400.0,
                unitOfMeasure = "г",
                description = "Консервированный нут"
            ),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Морковь"),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Лук репчатый"),
            IngredientDto(quantity = 3.0, unitOfMeasure = "зубчика", description = "Чеснок"),
            IngredientDto(
                quantity = 4.0,
                unitOfMeasure = "ст. ложки",
                description = "Панировочные сухари"
            ),
            IngredientDto(quantity = 2.0, unitOfMeasure = "ст. ложки", description = "Кунжут"),
            IngredientDto(
                quantity = 2.0,
                unitOfMeasure = "ст. ложки",
                description = "Растительное масло"
            ),
            IngredientDto(
                quantity = 4.0,
                unitOfMeasure = "штуки",
                description = "Веганские булочки"
            ),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Авокадо"),
            IngredientDto(quantity = 100.0, unitOfMeasure = "г", description = "Шпинат"),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Помидор"),
            IngredientDto(
                quantity = 4.0,
                unitOfMeasure = "ст. ложки",
                description = "Веганский майонез"
            ),
            IngredientDto(
                quantity = 2.0,
                unitOfMeasure = "ч. ложки",
                description = "Копченая паприка"
            ),
            IngredientDto(quantity = 1.0, unitOfMeasure = "ч. ложка", description = "Кумин (зира)")
        ),
        method = listOf(
            "1. Слейте жидкость с нута и разомните его вилкой в пюре, но не до однородности - оставьте немного целых горошин для текстуры.",
            "2. Натрите морковь на мелкой терке, мелко нарежьте лук и чеснок.",
            "3. Смешайте нут, морковь, лук, чеснок, панировочные сухари, кунжут, копченую паприку, кумин, соль и перец. Тщательно вымешайте массу.",
            "4. Сформируйте 4 котлеты. Уберите их в холодильник на 30 минут, чтобы они лучше держали форму.",
            "5. Разогрейте сковороду с растительным маслом и обжаривайте котлеты по 5-6 минут с каждой стороны до золотистой корочки.",
            "6. Разрежьте булочки и подрумяньте их на сухой сковороде.",
            "7. Нарежьте авокадо слайсами, помидор - кружочками.",
            "8. На нижнюю половинку булочки нанесите веганский майонез, выложите шпинат, котлету, слайсы авокадо и помидора.",
            "9. Накройте верхней половинкой булочки и подавайте с картофелем фри или овощным салатом."
        ),
        imageUrl = "burger-vegan.png"
    ),
    RecipeDto(
        id = 2,
        title = "Острый гамбургер",
        ingredients = listOf(
            IngredientDto(quantity = 400.0, unitOfMeasure = "г", description = "Говяжий фарш"),
            IngredientDto(quantity = 2.0, unitOfMeasure = "штуки", description = "Перец халапеньо"),
            IngredientDto(
                quantity = 1.0,
                unitOfMeasure = "штука",
                description = "Перец чили красный"
            ),
            IngredientDto(
                quantity = 4.0,
                unitOfMeasure = "ломтика",
                description = "Острый сыр пепперджек"
            ),
            IngredientDto(
                quantity = 4.0,
                unitOfMeasure = "штуки",
                description = "Булочки для бургера"
            ),
            IngredientDto(quantity = 1.0, unitOfMeasure = "штука", description = "Красный лук"),
            IngredientDto(quantity = 100.0, unitOfMeasure = "г", description = "Руккола"),
            IngredientDto(
                quantity = 3.0,
                unitOfMeasure = "ст. ложки",
                description = "Острый соус (табаско или шрирача)"
            ),
            IngredientDto(quantity = 3.0, unitOfMeasure = "ст. ложки", description = "Майонез"),
            IngredientDto(quantity = 2.0, unitOfMeasure = "ст. ложки", description = "Кетчуп"),
            IngredientDto(
                quantity = 1.0,
                unitOfMeasure = "ч. ложка",
                description = "Каенский перец"
            ),
            IngredientDto(
                quantity = 1.0,
                unitOfMeasure = "ч. ложка",
                description = "Копченая паприка"
            ),
            IngredientDto(quantity = 2.0, unitOfMeasure = "зубчика", description = "Чеснок")
        ),
        method = listOf(
            "1. Мелко нарежьте один халапеньо и половину красного чили (без семян, если хотите уменьшить остроту). Добавьте их в фарш вместе с каенским перцем, копченой паприкой, измельченным чесноком, солью и перцем. Тщательно вымешайте.",
            "2. Сформируйте 4 котлеты и уберите в холодильник на 15 минут.",
            "3. Приготовьте острый соус: смешайте майонез, острый соус (табаско) и кетчуп. Добавьте щепотку каенского перца по желанию.",
            "4. Оставшиеся халапеньо и чили нарежьте тонкими кружочками. Красный лук нарежьте кольцами.",
            "5. Разогрейте сковороду или гриль. Обжаривайте котлеты по 4 минуты с каждой стороны. За минуту до готовности положите сверху по ломтику сыра пепперджек.",
            "6. Разрежьте булочки и поджарьте их на сковороде до золотистости.",
            "7. На нижнюю половинку булочки выложите рукколу, затем котлету с расплавленным сыром.",
            "8. Сверху разложите кружочки халапеньо, чили и кольца красного лука.",
            "9. Щедро полейте острым соусом и накройте верхней половинкой булочки.",
            "10. Подавайте с охлажденным напитком, так как бургер действительно острый!"
        ),
        imageUrl = "burger-chili.png"
    )
)

fun getCategories(): List<CategoryDto> {
    return categories
}

fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> {
    return when (categoryId) {
        0 -> burgerRecipes
        else -> emptyList()
    }
}