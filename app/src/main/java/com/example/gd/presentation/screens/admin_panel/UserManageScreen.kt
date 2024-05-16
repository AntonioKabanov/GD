package com.example.gd.presentation.screens.admin_panel

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.gd.presentation.components.ManageButtonsComponent
import com.example.gd.presentation.components.TopAppBarAdmin
import com.example.gd.ui.theme.colorGray
import com.example.gd.util.Response

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UserManageScreen(
    navController: NavController,
    adminViewModel: AdminViewModel = hiltViewModel()
) {
    var addMenuShow by remember { mutableStateOf(false) }
    var editMenuShow by remember { mutableStateOf(false) }
    var deleteMenuShow by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val rolesArray = arrayOf("User", "Admin", "Manager")
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userRole by remember { mutableStateOf(rolesArray[0]) }
    var userPhone by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }


    val defaultField = "Пользователи"
    val addUserHeader = "Добавление пользователя"
    val editUserHeader = "Редактирование пользователя"
    val deleteUserHeader = "Удаление пользователя"

    val header = when {
        addMenuShow -> addUserHeader
        editMenuShow -> editUserHeader
        deleteMenuShow -> deleteUserHeader
        else -> defaultField
    }

    when(val response = adminViewModel.addNewUserState.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Пользователь успешно добавлен")
            }
        }
        is Response.Error -> {
            Toast(response.message)
        }
    }

    when(val response = adminViewModel.editAnotherUserState.value) {
        is Response.Loading -> {}
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Информация о пользователе успешно обновлена")
            }
        }
        is Response.Error -> {
            Toast(response.message)
        }
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
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    label = { Text("Имя пользователя (необязательно)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = userEmail,
                    onValueChange = {
                        userEmail = it
                    },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = userPassword,
                    onValueChange = {
                        userPassword = it
                    },
                    label = { Text("Пароль") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
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
                        value = userRole,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        label = { Text("Роль") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        rolesArray.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    userRole = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        adminViewModel.addNewUser(
                            email = userEmail,
                            password = userPassword,
                            userName = userName,
                            userRole = userRole
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
            }
            if (editMenuShow) {
                OutlinedTextField(
                    value = userEmail,
                    onValueChange = {
                        userEmail = it
                    },
                    label = { Text("Email целевого пользователя") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                    },
                    label = { Text("Имя пользователя") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = userPhone,
                    onValueChange = {
                        userPhone = it
                    },
                    label = { Text("Телефон") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = deliveryAddress,
                    onValueChange = {
                        deliveryAddress = it
                    },
                    label = { Text("Адрес доставки") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
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
                        value = userRole,
                        onValueChange = {},
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        label = { Text("Роль") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        rolesArray.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    userRole = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        adminViewModel.editAnotherUser(
                            email = userEmail,
                            userName = userName,
                            userRole = userRole,
                            phone = userPhone,
                            deliveryAddress = deliveryAddress
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Изменить данные",
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
            }
        }
    }
}