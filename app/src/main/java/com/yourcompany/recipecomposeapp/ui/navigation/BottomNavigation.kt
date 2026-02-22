package com.yourcompany.recipecomposeapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun BottomNavigation(
    currentScreen: ScreenId,
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.ButtonPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceBetweenButtons)
        ) {
            Button(
                onClick = onCategoriesClick,
                shape = RoundedCornerShape(Dimens.HeaderTextCornerRadius),
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = ScreenId.CATEGORIES.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }

            Button(
                onClick = onFavoriteClick,
                shape = RoundedCornerShape(Dimens.HeaderTextCornerRadius),
                modifier = Modifier
                    .weight(1f)
                    .height(Dimens.ButtonHeight),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = ScreenId.FAVORITES.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                    Spacer(modifier = Modifier.width(Dimens.ButtonIconPadding))
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}