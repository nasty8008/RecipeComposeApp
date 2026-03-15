package com.yourcompany.recipecomposeapp.features.details.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import kotlin.math.roundToInt

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        steps = 10,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.tertiary,
            activeTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            inactiveTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            activeTickColor = MaterialTheme.colorScheme.tertiaryContainer,
            inactiveTickColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    )
}