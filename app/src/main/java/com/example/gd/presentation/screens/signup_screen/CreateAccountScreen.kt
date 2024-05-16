package com.example.gd.presentation.screens.signup_screen

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.R
import com.example.gd.navigation.Screen
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorPrimary
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response
import kotlinx.coroutines.launch


@Composable
fun CreateAccountScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var userName by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else colorPrimary)
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "LoginScreen Logo",
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            TextField(value = email,
                leadingIcon = {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "",
                                tint = Color.Gray
                            )

                        }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = "Email") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    email = it
                })

            Spacer(modifier = Modifier.height(10.dp))

            /*var usermobilenumber by remember { mutableStateOf("") } // ВРЕМЕННО НЕДОСТУПНО
            val maxChar = 10
            TextField(
                singleLine = true,
                value = usermobilenumber,
                leadingIcon = {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Image(
                                painter = painterResource(id = R.drawable.indianflag),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(24.dp, 24.dp)
                                    .padding(start = 10.dp)
                            )
                            Text(
                                text = "+7",
                                color = colorBlack,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                            Canvas(
                                modifier = Modifier
                                    .height(24.dp)
                                    .padding(start = 10.dp)
                            ) {
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = 2.0F
                                )
                            }
                        }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                label = {
                    Text(
                        text = "Телефон",
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    if (it.length <= maxChar) usermobilenumber = it
                }
            )*/

            TextField(value = password,
                leadingIcon = {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "",
                                tint = Color.Gray
                            )

                        }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = "Пароль") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    password = it
                })

            Spacer(modifier = Modifier.height(10.dp))

            TextField(value = userName,
                leadingIcon = {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_accessibility_24),
                                contentDescription = "",
                                tint = Color.Gray
                            )

                        }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Имя пользователя (необязательно)") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    userName = it
                })

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.signUp(email, password, userName)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorBlack),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Зарегистрироваться",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                when(val response = viewModel.signUpState.value) {
                    is Response.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterVertically)
                                .padding(start = 5.dp),
                            color = colorWhite
                        )
                    }
                    is Response.Success -> {
                        if(response.data) {
                            Toast(message = "Регистрация прошла успешно")
                            LaunchedEffect(key1 = true) {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(Screen.CreateAccountScreen.route) {
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
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorRedLite),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Войти в существующий аккаунт",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
@Preview
fun CreateAccountScreenPreview() {
    CreateAccountScreen(
        navController = NavController(LocalContext.current),
        viewModel = hiltViewModel()
    )
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CreateAccountScreenDarkPreview() {
    CreateAccountScreen(
        navController = NavController(LocalContext.current),
        viewModel = hiltViewModel()
    )

}
