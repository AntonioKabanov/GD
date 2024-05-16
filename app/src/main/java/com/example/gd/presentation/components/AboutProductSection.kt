package com.example.gd.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutProductSection(
    modifier: Modifier = Modifier,
    weight: String,
    calories: String
) {
    Column(
        modifier = modifier
    ) {
        Divider()
        AboutProductSectionItem(
            descriptionName = "Масса",
            descriptionValue = "$weight г"
        )
        Divider()
        AboutProductSectionItem(
            descriptionName = "Пищевая ценность",
            descriptionValue = "$calories ккал"
        )
        Divider()
    }
}

@Composable
@Preview(showBackground = true)
fun AboutProductSectionPreview() {
    AboutProductSection(
        weight = "200",
        calories = "600"
    )
}