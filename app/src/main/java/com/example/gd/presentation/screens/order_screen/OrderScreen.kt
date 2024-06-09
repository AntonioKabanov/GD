package com.example.gd.presentation.screens.order_screen


import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gd.domain.model.Order
import com.example.gd.domain.model.Product
import com.example.gd.domain.repositories.MyOrdersDataDummy
import com.example.gd.presentation.navigation.Screen
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Products.ProductViewModel
import com.example.gd.presentation.components.TopAppBarMyOrders
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response

@Composable
fun OrderScreen(navController: NavHostController, checkAlertCount: (Int) -> Unit) {
    var alertCount by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBarMyOrders(navController)
        },
        backgroundColor = colorWhite,
        content = {
            OrderMainContent(
                navController,
                checkAlertCount = {
                    alertCount = it
                    checkAlertCount(alertCount)
                }
            )
        }
    )
}

@Composable
fun OrderMainContent(navController: NavController, checkAlertCount: (Int) -> Unit) {
    val productViewModel: ProductViewModel = hiltViewModel()
    var productInOrderList by remember { mutableStateOf(emptyList<Product>()) }
    val counterValuesList = remember { mutableStateListOf<Int>() }
    val deletedItems = remember { mutableStateListOf<String>() }

    productViewModel.getOrderById()
    when (val response = productViewModel.getOrderData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data != null) {
                productInOrderList = response.data.filter { it.id !in deletedItems }
                counterValuesList.clear()
                counterValuesList.addAll(List(productInOrderList.size) { 1 })
                checkAlertCount(productInOrderList.size)
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
    }

    when (val response = productViewModel.deleteFromOrderData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Продукт успешно удален")
                productInOrderList = productInOrderList.filter { it.id !in deletedItems }
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
    }

    Column {
        OrderList(
            productList = productInOrderList,
            counterList = counterValuesList,
            onValueChanged = { index, value ->
                counterValuesList[index] = value
            },
            onDelete = { productId ->
                deletedItems.add(productId)
                productViewModel.deleteFromOrder(productId)
            },
            productViewModel = productViewModel
        )
        OrderCalculateData(
            productInOrderList,
            navController,
            counterValuesList
        )
    }
}

@Composable
fun OrderCalculateData(
    productList: List<Product>,
    navController: NavController,
    counterValuesList: List<Int>
) {
    var deliveryPrice by remember { mutableStateOf(0) }
    var orderPrice = 0.0
    productList.forEachIndexed { index, product ->
        orderPrice += product.price * counterValuesList[index]
    }
    val counterValuesParcelable = ArrayList(counterValuesList)
    val fullPrice = orderPrice + deliveryPrice
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        //Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                          navController.navigate(Screen.OrderHistory.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorRedLite),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "История заказов",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Сумма заказа:",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = "$orderPrice ₽",
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Стоимость доставки:",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = "$deliveryPrice ₽",
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Итог:",
                    color = Color.Gray,
                    style = MaterialTheme.typography.button
                )
                Text(
                    text = "$fullPrice ₽",
                    color = colorRedDark,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("productList", productList)
                    navController.currentBackStackEntry?.savedStateHandle?.set("price", fullPrice)
                    navController.currentBackStackEntry?.savedStateHandle?.set("counterList", counterValuesParcelable)
                    navController.navigate(Screen.TrackOrderScreen.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorBlack),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(24.dp),
                enabled = productList.isNotEmpty()
            ) {
                Text(
                    text = "Оформить заказ",
                    color = if (productList.isNotEmpty()) Color.White else Color.Gray,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun OrderList(
    productList: List<Product>,
    counterList: List<Int>,
    productViewModel: ProductViewModel,
    onValueChanged: (Int, Int) -> Unit,
    onDelete: (String) -> Unit
) {
    if (productList.isNotEmpty()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxHeight(0.4f)
        ) {
            items(productList.size) { item ->
                MyOrdersListItem(
                    myOrders = productList[item],
                    counterValue = counterList[item],
                    onAddClick = {
                        val newValue = counterList[item] + 1
                        onValueChanged(item, newValue)
                    },
                    onSubClick = {
                        if (counterList[item] > 1) {
                            val newValue = counterList[item] - 1
                            onValueChanged(item, newValue)
                        } else {
                            onDelete(productList[item].id)
                        }
                    },
                    productViewModel = productViewModel
                )
            }
        }
    } else {
        Text(
            text = "Пока что здесь пусто",
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyOrdersListItem(
    myOrders: Product,
    counterValue: Int,
    productViewModel: ProductViewModel,
    onAddClick: () -> Unit,
    onSubClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GlideImage(
            model = myOrders.image,
            contentDescription = "",
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .weight(0.9f)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = myOrders.name,
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${myOrders.price} ₽",
                style = TextStyle(
                    fontWeight = FontWeight.W300,
                    color = Color.LightGray,
                    fontSize = 18.sp
                ),
                color = colorBlack,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(40.dp)
                .clip(shape = CircleShape)
                .background(colorRedLite)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(colorWhite)
                        .size(32.dp, 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    IconButton(onClick = onSubClick) {
                        Icon(
                            imageVector = Icons.Outlined.Remove,
                            contentDescription = "",
                            tint = colorRedDark,
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }

                Text(
                    text = counterValue.toString(),
                    color = colorBlack,
                    style = MaterialTheme.typography.button,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(Color.Red)
                        .size(32.dp, 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            tint = colorWhite,
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }
            }
        }
    }

    HorizontalDivider()
}

@Composable
fun HorizontalDivider() {
    Divider(
        color = colorRedLite, thickness = 1.dp,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    )

}

/*
@Composable
@Preview
fun OrderScreenPreview() {
    OrderScreen(navController = NavHostController(LocalContext.current))
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun OrderScreenDarkPreview() {
    OrderScreen(navController = NavHostController(LocalContext.current))
}*/
