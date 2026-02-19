package com.yourcompany.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yourcompany.recipecomposeapp.R
import com.yourcompany.recipecomposeapp.ScreenId
import com.yourcompany.recipecomposeapp.core.ui.ScreenHeader
import com.yourcompany.recipecomposeapp.ui.theme.Dimens

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize()
    ) {
        ScreenHeader(
            ScreenId.CATEGORIES.displayName,
            R.drawable.bcg_categories
        )
        LazyColumn(
            modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(10) { index ->
                Text(
                    text = "Категория ${index + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.HeaderTextPadding)
                )
            }
        }
    }
}