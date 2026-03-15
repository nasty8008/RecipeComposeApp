package com.yourcompany.recipecomposeapp.features.recipes.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.yourcompany.recipecomposeapp.data.model.IngredientDto
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class IngredientUiModel(
    val name: String,
    val amount: Double,
    val unitOfMeasure: String,
) : Parcelable

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    amount = quantity,
    unitOfMeasure = unitOfMeasure
)