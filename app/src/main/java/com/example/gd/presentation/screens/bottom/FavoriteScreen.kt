package com.example.gd.presentation.screens.bottom

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gd.R
import com.example.gd.domain.model.Product
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Products.ProductViewModel
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response

@Composable
fun FavoriteScreen(navController: NavController) {
    val productViewModel: ProductViewModel = hiltViewModel()
    var productInFavList by remember { mutableStateOf(emptyList<Product>()) }
    val deletedItems = remember { mutableStateListOf<String>() }

    productViewModel.getFavoriteById()
    when (val response = productViewModel.getFavoriteData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data != null) {
                productInFavList = response.data.filter { it.id !in deletedItems }
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
    }

    when (val response = productViewModel.deleteFromFavoriteData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Продукт успешно удален")
                productInFavList = productInFavList.filter { it.id !in deletedItems }
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
    }

    when (val response = productViewModel.addProductInOrderData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Товар добавлен в заказ")
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
    }
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Избранное",
                style = MaterialTheme.typography.h6,
                color = colorBlack
            )
            if (productInFavList.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxHeight(0.4f)
                ) {
                    items(productInFavList.size) { item ->
                        MyOrdersListItem(
                            myOrders = productInFavList[item],
                            onAddInOrderClick = {
                                productViewModel.addProductInOrder(productInFavList[item].id)
                            },
                            onDeleteClick = {
                                deletedItems.add(productInFavList[item].id)
                                productViewModel.deleteFromFavorite(productInFavList[item].id)
                            },
                            productViewModel = productViewModel
                        )
                    }
                }
            } else {
                Text(
                    text = "Пока что здесь пусто",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp).align(Alignment.Start)
                )
            }
        }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MyOrdersListItem(
    myOrders: Product,
    productViewModel: ProductViewModel,
    onAddInOrderClick: () -> Unit,
    onDeleteClick: () -> Unit
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
                .width(80.dp)
                .height(40.dp)
                .clip(shape = CircleShape)
                .background(Color.LightGray)
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
                        .size(32.dp, 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "",
                            tint = colorBlack,
                            modifier = Modifier.size(20.dp, 20.dp)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(32.dp, 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = onAddInOrderClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_shopping_cart_24),
                            contentDescription = "",
                            tint = colorBlack,
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
fun FavoriteScreenPreview() {
    FavoriteScreen(navController = NavController(LocalContext.current))
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun FavoriteScreenDarkPreview() {
    FavoriteScreen(navController = NavController(LocalContext.current))
}*/
