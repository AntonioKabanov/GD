package com.example.gd.presentation.screens.order_screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.R
import com.example.gd.domain.model.Product
import com.example.gd.presentation.navigation.Screen
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Products.OrderViewModel
import com.example.gd.presentation.Products.PointViewModel
import com.example.gd.presentation.Products.ProductViewModel
import com.example.gd.presentation.components.ProfileItem
import com.example.gd.presentation.components.TopAppBarMap
import com.example.gd.presentation.screens.bottom.UserViewModel
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorGray
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Constants
import com.example.gd.util.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TrackOrderScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel(),
    pointViewModel: PointViewModel = hiltViewModel(),
    productList: List<Product>,
    countList: List<Int>,
    price: Double
) {
    var isPoint by remember { mutableStateOf(false) }
    var useLoyaltyPoints by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var loyaltyPoints by remember { mutableStateOf(0) }
    var pointAddress by remember { mutableStateOf("") }
    var orderType = Constants.GET_IN_POINT

    val effectivePrice = if (useLoyaltyPoints) price - loyaltyPoints else price

    pointViewModel.getPointByUser()
    when(val response = pointViewModel.getCurrentPointData.value) {
        is Response.Loading -> {}
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if(response.data != null) {
                LaunchedEffect(key1 = true) {
                    pointAddress = response.data.pointAddress
                }
            }
        }
    }

    userViewModel.getUserInfo()
    when(val response = userViewModel.getUserData.value) {
        is Response.Loading -> {}
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if(response.data != null) {
                LaunchedEffect(key1 = true) {
                    Log.d("Successful", response.data.toString())
                    userName = response.data.userName
                    phone = response.data.phone
                    deliveryAddress = response.data.deliveryAddress
                    loyaltyPoints = response.data.loyaltyPoints
                }
            }
        }
    }

    when (val response = orderViewModel.arrangeOrderData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Заказ успешно оформлен. Следите за статусом")
                navController.navigate(Screen.OrderScreen.route) {
                    popUpTo(Screen.TrackOrderScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
    }


    Scaffold(topBar = {
        TopAppBarMap(navController)
    }, backgroundColor = colorWhite,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Text(
                    text = "Информация о заказе:",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W300
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProfileItem(
                    imageResource = R.drawable.baseline_accessibility_24,
                    contentDescription = null,
                    text = userName
                )
                ProfileItem(
                    imageResource = R.drawable.baseline_phone_24,
                    contentDescription = null,
                    text = phone
                )
                if (!isPoint) {
                    ProfileItem(
                        imageResource = R.drawable.baseline_delivery_dining_24,
                        contentDescription = null,
                        text = deliveryAddress
                    )
                    orderType = Constants.GET_BY_DELIVERY
                }
                if (isPoint) {
                    ProfileItem(
                        imageResource = R.drawable.baseline_home_work_24,
                        contentDescription = null,
                        text = pointAddress
                    )
                    orderType = Constants.GET_IN_POINT
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Состав заказа:",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W300
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                productList.zip(countList).forEach { (product, count) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 15.dp)
                    ) {
                        Text(
                            text = "${product.name}: $count шт.",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.W300
                            )
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (effectivePrice != 0.0) {
                    Text(
                        text = "Общая стоимость: $effectivePrice руб",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.W500
                        )
                    )
                }

                Text(
                    text = "Использовать баллы лояльности: $loyaltyPoints",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W300
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = useLoyaltyPoints,
                        onCheckedChange = { useLoyaltyPoints = it }
                    )
                    Text(
                        text = "Применить баллы",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.W300
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = isPoint,
                        onClick = { isPoint = true }
                    )
                    Text(
                        text = "Самовывоз",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.W300
                        )
                    )
                    RadioButton(
                        selected = !isPoint,
                        onClick = { isPoint = false }
                    )
                    Text(
                        text = "Доставка",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.W300
                        )
                    )
                }
                //Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        orderViewModel.arrangeOrder(
                            products = productList,
                            counts = countList,
                            orderType = orderType,
                            deliveryAddress = deliveryAddress,
                            pointAddress = pointAddress,
                            totalPrice = effectivePrice
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorBlack),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Оформить заказ",
                        color = colorWhite,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                }

            }
        })

}



/*
@Composable
@Preview
fun TrackOrderScreenPreview() {
    TrackOrderScreen()
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TrackOrderScreenDarkPreview() {
    TrackOrderScreen()
}*/
