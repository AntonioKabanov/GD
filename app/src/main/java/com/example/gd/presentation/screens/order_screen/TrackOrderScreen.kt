package com.example.gd.presentation.screens.order_screen

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.gd.presentation.components.TopAppBarMap
import com.example.gd.ui.theme.colorWhite

@Composable
fun TrackOrderScreen() {
    Scaffold(topBar = {
        TopAppBarMap()
    }, backgroundColor = if (isSystemInDarkTheme()) Color.Black else colorWhite,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {

            }
        })


}

@Composable
@Preview
fun TrackOrderScreenPreview() {
    TrackOrderScreen()
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TrackOrderScreenDarkPreview() {
    TrackOrderScreen()
}