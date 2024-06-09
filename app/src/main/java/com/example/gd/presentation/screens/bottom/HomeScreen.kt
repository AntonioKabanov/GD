package com.example.gd.presentation.screens.bottom

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.gd.R
import com.example.gd.domain.model.Category
import com.example.gd.domain.model.Product
import com.example.gd.presentation.navigation.Screen
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Categories.CategoryViewModel
import com.example.gd.presentation.Products.ProductViewModel
import com.example.gd.presentation.components.CategoryTabs
import com.example.gd.presentation.components.TopAppBarHome
import com.example.gd.presentation.screens.product_screen.ProductScreen
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorRedGrayLight
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthenticationViewModel,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Настройки",
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                    IconButton(onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Закрыть",
                            tint = Color.White
                        )
                    }
                }
                Divider()
                Text(
                    text = "Тех. поддержка",
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.SupportScreenUser.route) }
                        .padding(16.dp)
                )
                Text(
                    text = "О приложении",
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.AboutScreen.route) }
                        .padding(16.dp)
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorWhite)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(0.dp, (-30).dp),
                    painter = painterResource(id = R.drawable.bg_main),
                    contentDescription = "Header Background",
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    TopAppBarHome(
                        onDashboardClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Title()
                    Spacer(modifier = Modifier.height(20.dp))
                    Content(navController)
                }
            }
        }
    )
}


@Composable
fun Title() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Что бы вы хотели\n" +
                    "съесть сегодня?",
            color = colorBlack,
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun Content(navController: NavController) {
    var selectedCategoryId by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        Header(onSearchQueryChanged = { query ->
            searchQuery = query
        })
        Spacer(modifier = Modifier.height(16.dp))
        CategorySection(selectedCategoryId = { categoryId ->
            selectedCategoryId = categoryId
        })
        Spacer(modifier = Modifier.height(20.dp))
        PopularSection(
            navController = navController,
            categoryId = selectedCategoryId,
            searchQuery = searchQuery
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun Header(
    onSearchQueryChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var userSearch by remember { mutableStateOf("") }

        Card(
            modifier = Modifier.height(50.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(24.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                value = userSearch,
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                onValueChange = {
                    userSearch = it
                    onSearchQueryChanged(it)
                },
                placeholder = {
                    Text(
                        text = "Поиск по меню",
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        tint = Color.Gray
                    )
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = CircleShape)
                            .background(colorRedDark)
                    ) {
                        IconButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Filled.FilterList,
                                contentDescription = "",
                                tint = colorWhite
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun CategorySection(
    selectedCategoryId: (String) -> Unit
) {
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    var categoryItemList = emptyList<Category>()

    categoryViewModel.getCategoryList()
    when(val response = categoryViewModel.getCategoryData.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            categoryItemList = response.data
        }
        is Response.Error -> {
            Toast(response.message)
        }
    }

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Категории",
                color = colorBlack,
                style = MaterialTheme.typography.h6
            )
            TextButton(
                onClick = {
                }
            ) {
                Text(
                    text = "Просмотреть всё",
                    color = colorRedDark
                )
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = colorRedDark
                )
            }
        }

        if (categoryItemList.isNotEmpty()) {
            var selectedCategory by remember { mutableStateOf(categoryItemList.first()) }
            CategoryTabs(
                categories = categoryItemList,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                    if (category.name != "Всё")
                        selectedCategoryId(category.id)
                    else
                        selectedCategoryId("")
                }
            )
        }
        else {
            Text(
                text = "Получение списка категорий..."
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PopularSection(
    navController: NavController,
    categoryId: String,
    searchQuery: String
) {
    Column {
        val productViewModel: ProductViewModel = hiltViewModel()
        var productItemList by remember { mutableStateOf(emptyList<Product>()) }

        var isProductAdded by remember { mutableStateOf(false)}

        LaunchedEffect(categoryId, searchQuery) {
            if (categoryId.isEmpty()) {
                productViewModel.getProductList()
            } else {
                productViewModel.getProductsByCategory(categoryId)
            }
        }

        when (val response = productViewModel.getProductData.value) {
            is Response.Loading -> {}
            is Response.Success -> {
                productItemList = response.data.filter { product ->
                    product.name.contains(searchQuery, ignoreCase = true)
                }
            }
            is Response.Error -> {
                Toast(response.message)
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
                Toast(response.message)
            }
        }

        Text(
            text = "Меню \uD83D\uDD25",
            style = MaterialTheme.typography.h6,
            color = colorBlack
        )

        if (productItemList.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                content = {
                    items(productItemList.size) { item ->
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .wrapContentHeight()
                                .padding(10.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .border(width = 1.dp, color = Color.Gray)
                                .clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "productItem",
                                        productItemList[item]
                                    )
                                    navController.navigate(Screen.ProductScreen.route)
                                }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                GlideImage(
                                    modifier = Modifier
                                        .size(120.dp),
                                    model = productItemList[item].image,
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit
                                )

                                Text(
                                    text = productItemList[item].name,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.h6,
                                    color = colorBlack,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    colorRedDark,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(productItemList[item].price.toInt().toString())
                                            }
                                            withStyle(
                                                style = SpanStyle(
                                                    colorRedDark
                                                )
                                            ) {
                                                append(" ₽")
                                            }
                                        },
                                        style = MaterialTheme.typography.h6,
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(colorRedDark)
                                            .padding(4.dp)
                                            .clickable {
                                                productViewModel.addProductInOrder(
                                                    productId = productItemList[item].id
                                                )
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(20.dp, 20.dp),
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add",
                                            tint = colorWhite
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        } else {
            Text(
                text = "Получение списка меню..."
            )
        }
    }
}


/*
@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        navController = NavController(LocalContext.current),
    )
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenDarkPreview() {
    HomeScreen(navController = NavController(LocalContext.current))
}*/



