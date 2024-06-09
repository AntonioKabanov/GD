package com.example.gd.presentation.screens.admin_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.domain.model.Category
import com.example.gd.domain.model.Product
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Categories.CategoryViewModel
import com.example.gd.presentation.Products.ProductViewModel
import com.example.gd.presentation.components.ManageButtonsComponent
import com.example.gd.presentation.components.TopAppBarAdmin
import com.example.gd.util.Response

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProductManageScreen(
    navController: NavController,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    var categoryItemList = emptyList<Category>()
    var productItemList = emptyList<Product>()

    var addMenuShow by remember { mutableStateOf(false) }
    var editMenuShow by remember { mutableStateOf(false) }
    var deleteMenuShow by remember { mutableStateOf(false) }

    var productName by remember { mutableStateOf("") }
    var productImage by remember { mutableStateOf("0") }
    var productWeight by remember { mutableStateOf("") }
    var productCalories by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var categoryName by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val defaultField = "Позиции меню"
    val addProductHeader = "Добавление позиции"
    val editProductHeader = "Редактирование позиции"
    val deleteProductHeader = "Удаление позиции"


    val header = when {
        addMenuShow -> addProductHeader
        editMenuShow -> editProductHeader
        deleteMenuShow -> deleteProductHeader
        else -> defaultField
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        TopAppBarAdmin(
            headerName = header,
            onBackClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(bottom = 10.dp)
        )
        if (!addMenuShow && !editMenuShow && !deleteMenuShow) {
            ManageButtonsComponent(
                onAddClick = {
                    addMenuShow = true
                    editMenuShow = false
                    deleteMenuShow = false
                },
                onEditClick = {
                    addMenuShow = false
                    editMenuShow = true
                    deleteMenuShow = false
                },
                onDeleteClick = {
                    addMenuShow = false
                    editMenuShow = false
                    deleteMenuShow = true
                }
            )
        }
        else {
            if (addMenuShow) {
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

               val selectedCategoryListItem = if (categoryItemList.size > 1) categoryItemList[1].name else "Пусто"
               // categoryName = selectedCategoryListItem

                OutlinedTextField(
                    value = productName,
                    onValueChange = {
                        productName = it
                    },
                    label = { Text("Наименование позиции") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = productImage,
                    onValueChange = {
                        productImage = it
                    },
                    label = { Text("Фото позиции меню (необязательно)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = productWeight,
                    onValueChange = {
                        productWeight = it
                    },
                    label = { Text("Масса продукта (г)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = productCalories,
                    onValueChange = {
                        productCalories = it
                    },
                    label = { Text("Калорийность (ккал)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = {
                        productPrice = it
                    },
                    label = { Text("Стоимость (руб)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = categoryName,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        label = { Text("Категория") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    )
                    if (categoryItemList.size > 1) {
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categoryItemList.forEach { item ->
                                if (item.name != "Всё") {
                                    DropdownMenuItem(
                                        text = { Text(text = item.name) },
                                        onClick = {
                                            categoryName = item.name
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        if (selectedCategoryListItem == "Пусто")
                            categoryName = "Всё"
                        productViewModel.addProduct(
                            name = productName,
                            image = productImage,
                            weight = productWeight,
                            calories = productCalories,
                            price = productPrice.toDouble(),
                            categoryName = categoryName
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Добавить",
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
                when(val response = productViewModel.addProductData.value) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        if (response.data) {
                            Toast(message = "Позиция успешно добавлена")
                        }
                    }
                    is Response.Error -> {
                        Toast(response.message)
                    }
                }
            }
            if (deleteMenuShow) {
                productViewModel.getProductList()
                when(val response = productViewModel.getProductData.value) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        productItemList = response.data
                    }
                    is Response.Error -> {
                        Toast(response.message)
                    }
                }

                val selectedProductListItem = if (productItemList.isNotEmpty()) productItemList.first().name else "Пусто"
                productName = selectedProductListItem

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = productName,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        label = { Text("Позиция меню") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    )
                    if (productItemList.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            productItemList.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.name) },
                                    onClick = {
                                        productName = item.name
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        if (productItemList.isNotEmpty())
                            productViewModel.deleteProduct(productName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Удалить",
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
                when(val response = productViewModel.deleteProductData.value) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        if (response.data) {
                            Toast(message = "Позиция успешно удалена")
                        }
                    }
                    is Response.Error -> {
                        Toast(response.message)
                    }
                }
            }
        }
    }
}
