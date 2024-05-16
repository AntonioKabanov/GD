package com.example.gd.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gd.ui.theme.colorBlack

@Composable
fun TopAppBarAdmin(
    modifier: Modifier = Modifier,
    headerName: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "",
                tint = colorBlack
            )

        }
        Text(
            text = headerName,
            color = colorBlack,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 76.dp)
        )
    }
}

@Composable
@Preview
fun TopAppBarAdminPreview() {
    TopAppBarAdmin(
        headerName = "Admin",
        onBackClick = {}
    )
}