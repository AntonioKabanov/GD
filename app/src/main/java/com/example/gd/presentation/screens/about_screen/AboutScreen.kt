package com.example.gd.presentation.screens.about_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gd.R
import com.example.gd.presentation.components.TopAppBarAbout
import com.example.gd.presentation.components.TopAppBarMyOrders
import com.example.gd.presentation.screens.order_screen.OrderCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBarAbout(navController)
        },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.gd_logo),
                    contentDescription = "SplashScreenLogo",
                    modifier = Modifier.size(250.dp)
                )
                Text(
                    text = "GD ver 1.0",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        fontWeight = FontWeight.W300
                    ),
                    modifier = Modifier.padding(bottom = 16.dp).align(Alignment.End)
                )
                Text(
                    text = "Приложение разработано студентом ЯГТУ Кабановым Антоном Сергеевичем.",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        fontWeight = FontWeight.W300
                    ),
                    modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    AboutScreen(NavController(LocalContext.current))
}