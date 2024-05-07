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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gd.data.MyOrders
import com.example.gd.data.repositories.MyOrdersDataDummy
import com.example.gd.presentation.components.TopAppBarMyOrders
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite

@Composable
fun OrderScreen(navController: NavHostController) {
    Scaffold(topBar = {
        TopAppBarMyOrders()
    },
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else colorWhite,
        content = {
            OrderMainContent()
        })

}

@Composable
fun OrderMainContent() {
    Column(
        //modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        OrderList()
        OrderCalculateData()
    }
}

@Composable
fun OrderCalculateData() {
    Column(modifier = Modifier
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

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorRedLite),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Применить промокод",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
            //Spacer(modifier = Modifier.height(2.dp))
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
                    text = "$14.95",
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
                    text = "$2.25",
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
                    text = "$20.15",
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

    }
}

@Composable
fun OrderList() {
    val myOrdersTitle = remember { MyOrdersDataDummy.myOrdersList }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxHeight(0.4f)
    ) {
        items(
            items = myOrdersTitle,
            itemContent = {
                MyOrdersListItem(myOrders = it)
            })
    }

}

@Composable
fun MyOrdersListItem(
    myOrders: MyOrders
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = myOrders.ordersImageId),
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
                text = "${myOrders.price}",
                style = MaterialTheme.typography.h6,
                color = colorRedDark,
                fontWeight = FontWeight.Bold
            )

        }
        val counter = remember { mutableStateOf(1) }
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
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        if (counter.value != 0)
                            counter.value--
                    }) {
                        Icon(
                            imageVector = Icons.Default.Minimize,
                            contentDescription = "",
                            tint = colorRedDark,
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }

                Text(
                    text = "${counter.value}",
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
                    IconButton(onClick = {
                        counter.value++
                    }) {
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

@Composable
@Preview
fun OrderScreenPreview() {
    OrderScreen(navController = NavHostController(LocalContext.current))
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun OrderScreenDarkPreview() {
    OrderScreen(navController = NavHostController(LocalContext.current))
}