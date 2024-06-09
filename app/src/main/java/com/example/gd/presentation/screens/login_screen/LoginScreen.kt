package com.example.gd.presentation.screens.login_screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.R
import com.example.gd.presentation.navigation.Screen
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.ui.theme.*
import com.example.gd.util.Response
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorGray)
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
                painter = painterResource(id = R.drawable.gd_logo),
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
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
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
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    password = it
                })
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    viewModel.signIn(email, password)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorBlack),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Войти в аккаунт",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                when(val response = viewModel.signInState.value) {
                    is Response.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp)
                                .align(Alignment.CenterVertically)
                                .padding(start = 5.dp),
                            color = colorWhite
                        )
                    }
                    is Response.Success -> {
                        if(response.data) {
                            Toast(message = "Вход прошел успешно")
                            LaunchedEffect(key1 = true) {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(Screen.LoginScreen.route) {
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
                    navController.navigate(Screen.CreateAccountScreen.route) {
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
                    text = "Создать новый аккаунт",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = { }) {
                    Text(
                        text = "Забыли пароль?",
                        color = colorWhite,
                        style = MaterialTheme.typography.button,
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(
        navController = NavController(LocalContext.current),
        viewModel = hiltViewModel())
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginScreenDarkPreview() {
    LoginScreen(
        navController = NavController(LocalContext.current),
        viewModel = hiltViewModel()
    )

}