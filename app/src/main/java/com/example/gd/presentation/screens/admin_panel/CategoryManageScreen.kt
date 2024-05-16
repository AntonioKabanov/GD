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
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Categories.CategoryViewModel
import com.example.gd.presentation.components.ManageButtonsComponent
import com.example.gd.presentation.components.TopAppBarAdmin
import com.example.gd.util.Response

@Composable
fun CategoryManageScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    var addMenuShow by remember { mutableStateOf(false) }
    var editMenuShow by remember { mutableStateOf(false) }
    var deleteMenuShow by remember { mutableStateOf(false) }

    var categoryName by remember { mutableStateOf("") }
    var categoryPhotoUrl by remember { mutableStateOf("") }

    val defaultField = "Категории"
    val addCategoryHeader = "Добавление категории"
    val editCategoryHeader = "Редактирование категории"
    val deleteCategoryHeader = "Удаление категории"

    val header = when {
        addMenuShow -> addCategoryHeader
        editMenuShow -> editCategoryHeader
        deleteMenuShow -> deleteCategoryHeader
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
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = {
                        categoryName = it
                    },
                    label = { Text("Наименование категории") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = categoryPhotoUrl,
                    onValueChange = {
                        categoryPhotoUrl = it
                    },
                    label = { Text("Фото категории (необязательно)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        categoryViewModel.addNewCategory(
                            name = categoryName,
                            categoryPhotoUrl = categoryPhotoUrl
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
                when(val response = categoryViewModel.deleteCategoryData.value) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        if (response.data) {
                            Toast(message = "Категория успешно удалена")
                        }
                    }
                    is Response.Error -> {
                        Toast(response.message)
                    }
                }
            }
            if (deleteMenuShow) {
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = {
                        categoryName = it
                    },
                    label = { Text("Наименование категории") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        categoryViewModel.deleteNewCategory(categoryName)
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
                when(val response = categoryViewModel.addCategoryData.value) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        if (response.data) {
                            Toast(message = "Категория успешно добавлена")
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
