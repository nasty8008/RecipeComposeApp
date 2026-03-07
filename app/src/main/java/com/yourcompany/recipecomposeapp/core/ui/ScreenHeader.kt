package com.yourcompany.recipecomposeapp.core.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun ScreenHeader(
    title: String,
    image: Any,
    showShareButton: Boolean = false,
    onShareClick: (() -> Unit)? = null,
    onFavoriteToggle: (() -> Unit)? = null,
    isFavorite: Boolean = false,
    showFavoriteButton: Boolean = false
) {

    val context = LocalContext.current
    val imageRequest = remember(image) {
        ImageRequest.Builder(context)
            .data(image)
            .crossfade(true)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .build()
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeaderHeight)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop
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
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(Dimens.HeaderTextPadding)
        ) {

            if (showShareButton && onShareClick != null) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }

            if (showFavoriteButton && onFavoriteToggle != null) {
                IconButton(onClick = onFavoriteToggle) {

                    Crossfade(
                        targetState = isFavorite,
                        animationSpec = tween(durationMillis = 300),
                        label = "favorite_animation"
                    ) { isCurrentlyFavorite ->

                        val painter = rememberVectorPainter(
                            image = ImageVector.vectorResource(
                                id = if (isCurrentlyFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
                            )
                        )

                        Icon(
                            painter = painter,
                            contentDescription = "favorite",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}