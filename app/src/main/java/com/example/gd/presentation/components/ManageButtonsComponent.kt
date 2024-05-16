package com.example.gd.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gd.navigation.Screen
import com.example.gd.ui.theme.colorGray
import com.example.gd.ui.theme.colorWhite

@Composable
fun ManageButtonsComponent(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        TextButton(
            onClick = onAddClick,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorGray, RoundedCornerShape(10.dp))
                .border(2.dp, Color.Green, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Добавить",
                style = MaterialTheme.typography.button,
                color = Color.Green
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorGray, RoundedCornerShape(10.dp))
                .border(2.dp, Color.Blue, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Изменить",
                style = MaterialTheme.typography.button,
                color = Color.Blue
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorGray, RoundedCornerShape(10.dp))
                .border(2.dp, Color.Red, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Удалить",
                style = MaterialTheme.typography.button,
                color = Color.Red
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ManageButtonsComponentPreview() {
    ManageButtonsComponent(
        onAddClick = {},
        onEditClick = {},
        onDeleteClick = {}
    )
}