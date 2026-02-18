package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun ScreenHeader(title: String, imageId: Int) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeaderHeight)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(imageId),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Surface(
            shape = RoundedCornerShape(Dimens.HeaderTextCornerRadius),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.HeaderTextPadding)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimens.HeaderTextInnerPadding)
            )
        }
    }
}