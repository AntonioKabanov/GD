package com.example.gd.presentation.screens.bottom

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.R
import com.example.gd.presentation.navigation.Screen
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.components.ProfileHeader
import com.example.gd.presentation.components.ProfileItem
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorGray
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Constants
import com.example.gd.util.Response

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    authViewModel: AuthenticationViewModel
) {
    var userId by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userRole by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var registrationDate by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var loyaltyPoints by remember { mutableStateOf("") }
    var isEditOpen by remember { mutableStateOf(false) }
    var isFieldChanged by remember { mutableStateOf(false) }
    val unknownUser = "user@${userId.take(5)}"
    val defaultField = "Нет данных"
    val emptyField = "Поле не заполнено"

    when(val response = authViewModel.deleteUserState.value) {
        is Response.Loading -> {
            CircularProgressIndicator()
        }
        is Response.Success -> {
            if(response.data) {
                Toast(message = "Аккаунт успешно удален")
                LaunchedEffect(key1 = true) {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.ProfileScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        is Response.Error -> {
            Toast(message = response.message)
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
                    userId = response.data.userid
                    userName = response.data.userName
                    userRole = response.data.role
                    email = response.data.email
                    phone = response.data.phone
                    registrationDate = response.data.registrationDate
                    deliveryAddress = response.data.deliveryAddress
                    loyaltyPoints = response.data.loyaltyPoints.toString()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.h5,
                color = colorBlack,
                modifier = Modifier.padding(top = 15.dp)
            )
            if (isEditOpen) {
                OutlinedTextField(
                    value = userName,
                    onValueChange = {
                        userName = it
                        isFieldChanged = true
                    },
                    label = {Text("Имя пользователя")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        isFieldChanged = true
                    },
                    label = {Text("Телефон")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
                OutlinedTextField(
                    value = deliveryAddress,
                    onValueChange = {
                        deliveryAddress = it
                        isFieldChanged = true
                    },
                    label = {Text("Адрес доставки")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                )
            }
            else {
                ProfileHeader(
                    imageResource = R.drawable.ic_launcher_background,
                    contentDescription = null,
                    userName = userName.ifEmpty { unknownUser },
                    userRole = userRole,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                ProfileItem(
                    imageResource = R.drawable.baseline_phone_24,
                    contentDescription = null,
                    text = phone.ifEmpty { emptyField },
                    modifier = Modifier.padding(start = 20.dp)
                )
                ProfileItem(
                    imageResource = R.drawable.baseline_mail_24,
                    contentDescription = null,
                    text = email.ifEmpty { defaultField },
                    modifier = Modifier.padding(start = 20.dp)
                )
                ProfileItem(
                    imageResource = R.drawable.baseline_delivery_dining_24,
                    contentDescription = null,
                    text = deliveryAddress.ifEmpty { emptyField },
                    modifier = Modifier.padding(start = 20.dp)
                )
                ProfileItem(
                    imageResource = R.drawable.baseline_access_time_24,
                    contentDescription = null,
                    text = registrationDate.ifEmpty { defaultField },
                    modifier = Modifier.padding(start = 20.dp)
                )
                ProfileItem(
                    imageResource = R.drawable.baseline_monetization_on_24,
                    contentDescription = null,
                    text = loyaltyPoints,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
            ) {
                if (isEditOpen) {
                    TextButton(
                        onClick = {
                            userViewModel.setUserInfo(
                                userName = userName,
                                phone = phone,
                                deliveryAddress = deliveryAddress
                            )
                            isEditOpen = false
                        },
                        modifier = Modifier
                            .background(colorRedDark, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Сохранить",
                            style = MaterialTheme.typography.button,
                            color = colorWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    TextButton(
                        onClick = {
                            userViewModel.getUserInfo()
                            isEditOpen = false
                        },
                        modifier = Modifier
                            .background(colorRedDark, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Отменить изменения",
                            style = MaterialTheme.typography.button,
                            color = colorWhite
                        )
                    }
                }
                else {
                    TextButton(
                        onClick = { isEditOpen = true },
                        modifier = Modifier
                            .background(colorRedDark, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Редактировать",
                            style = MaterialTheme.typography.button,
                            color = colorWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    TextButton(
                        onClick = {
                            authViewModel.signOut()
                        },
                        modifier = Modifier
                            .background(colorRedDark, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Выйти из аккаунта",
                            style = MaterialTheme.typography.button,
                            color = colorWhite
                        )
                    }
                }

                when(val response = authViewModel.signOutState.value) {
                    is Response.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Response.Success -> {
                        if(response.data) {
                            Toast(message = "Вы успешно вышли из аккаунта")
                            LaunchedEffect(key1 = true) {
                                navController.navigate(Screen.LoginScreen.route) {
                                    popUpTo(Screen.ProfileScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                    is Response.Error -> {
                        Toast(message = response.message)
                    }
                }
            }
            if(!isEditOpen) {
                if(userRole == Constants.ROLE_ADMIN) {
                    TextButton(
                        onClick = {
                            navController.navigate(Screen.AdminPanelScreen.route)
                        },
                        modifier = Modifier
                            .width(310.dp)
                            .background(Color.Red, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Админ-панель",
                            style = MaterialTheme.typography.button,
                            color = colorWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                if(userRole == Constants.ROLE_MANAGER) {
                    TextButton(
                        onClick = {
                            navController.navigate(Screen.ManageOrderScreen.route)
                        },
                        modifier = Modifier
                            .width(310.dp)
                            .background(Color.Red, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Панель менеджера",
                            style = MaterialTheme.typography.button,
                            color = colorWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                TextButton(
                    onClick = {
                        authViewModel.deleteUser()
                    },
                    modifier = Modifier
                        .width(310.dp)
                        .background(colorGray, RoundedCornerShape(10.dp))
                        .border(1.dp, Color.Red, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Удалить аккаунт",
                        style = MaterialTheme.typography.button,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

/*
@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(
        navController = NavController(LocalContext.current),
    )
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfileScreenDarkPreview() {
    ProfileScreen(
        navController = NavController(LocalContext.current),
    )
}*/
