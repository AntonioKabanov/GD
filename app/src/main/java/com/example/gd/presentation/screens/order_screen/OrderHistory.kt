package com.example.gd.presentation.screens.order_screen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import com.example.gd.domain.model.Order
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Products.OrderViewModel
import com.example.gd.presentation.components.TopAppBarMap
import com.example.gd.presentation.components.TopAppBarMyOrders
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Constants
import com.example.gd.util.Response

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderHistory(
    navController: NavController,
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    orderViewModel.getOrderListByUser()

    var orderList by remember { mutableStateOf(emptyList<Order>()) }

    when (val response = orderViewModel.getOrderData.value) {
        is Response.Loading -> {
            CircularProgressIndicator(
            )
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if (response.data != null) {
                orderList = response.data
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBarMyOrders(navController)
        },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "История заказов",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn {
                    items(orderList) { order ->
                        OrderCard(order = order)
                    }
                }
            }
        }
    )
}

@Composable
fun OrderCard(order: Order) {

    val statusColor: Color = when(order.status) {
        Constants.ORDER_CREATED -> Color.Gray
        Constants.ORDER_ACCEPT -> Color.Blue
        Constants.ORDER_CANCEL -> Color.Red
        Constants.ORDER_FINISH -> Color.Blue
        else -> {Color.Gray}
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Заказ № ${order.id.take(4)}",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = "Статус: ${order.status}",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = statusColor,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
            if (order.orderType == Constants.GET_BY_DELIVERY) {
                Text(
                    text = "Адрес доставки: ${order.deliveryAddress}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            if (order.orderType == Constants.GET_IN_POINT) {
                Text(
                    text = "Адрес пункта выдачи: ${order.pointAddress}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Text(
                text = "Состав заказа:",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            order.productNames.zip(order.productCounts).forEach { (name, count) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = "$count шт.",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            if (order.totalPrice != 0.0) {
                Text(
                    text = "Общая стоимость: ${order.totalPrice} руб",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

