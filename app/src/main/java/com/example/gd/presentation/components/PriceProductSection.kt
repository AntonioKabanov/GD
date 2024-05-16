package com.example.gd.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gd.R
import com.example.gd.navigation.Screen
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorGray
import com.example.gd.ui.theme.colorWhite

@Composable
fun PriceProductSection(
    price: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = {},
            modifier = Modifier
                .background(colorGray, RoundedCornerShape(10.dp))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_shopping_cart_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 5.dp)
            )
            Text(
                text = "Добавить ($price руб)",
                style = MaterialTheme.typography.button,
                color = Color.Black
            )
        }
        TextButton(
            onClick = {},
            modifier = Modifier
                .background(colorGray, RoundedCornerShape(10.dp))
                .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_favorite_24),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .padding(end = 5.dp)
            )
            Text(
                text = "В избранное",
                style = MaterialTheme.typography.button,
                color = colorBlack
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PriceProductSectionPreview() {
    PriceProductSection(
        price = 199.0.toString()
    )
}