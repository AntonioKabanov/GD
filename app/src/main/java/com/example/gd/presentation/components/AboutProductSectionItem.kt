package com.example.gd.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AboutProductSectionItem(
    modifier: Modifier = Modifier,
    descriptionName: String,
    descriptionValue: String,

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = descriptionName,
        )
        Text(
            text = descriptionValue,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AboutProductSectionItemPreview() {
    AboutProductSectionItem(
        descriptionName = "Масса",
        descriptionValue = "200г"
    )
}