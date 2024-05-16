package com.example.gd.presentation.screens.product_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.gd.domain.model.Product
import com.example.gd.presentation.components.AboutProductSection
import com.example.gd.presentation.components.PriceProductSection
import com.example.gd.presentation.components.TopAppBarProduct
import com.example.gd.ui.theme.colorGray

@Composable
fun ProductScreen(
    navController: NavController,
    product: Product,
    contentDescription: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TopAppBarProduct(
            onBackClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Image(
            painter = painterResource(product.ordersImageId),
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .size(300.dp)
        )
        Text(
            text = product.name,
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight.W500

            ),
            modifier = Modifier
                .padding(start = 10.dp, top = 8.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Описание:",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            ),
            modifier = Modifier
                .padding(start = 10.dp, bottom = 15.dp)
        )
        AboutProductSection(
            weight = product.weight,
            calories = product.calories
        )
        Spacer(modifier = Modifier.weight(1f))
        PriceProductSection(
            price = product.price.toString(),
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProductScreenPreview() {
    ProductScreen(
        navController = NavController(LocalContext.current),
        product = Product(
            name = "Шаверма питерская",
            weight = "200",
            calories = "600",
            price = 199.0
        )
    )
}