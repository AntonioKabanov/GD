package com.example.gd.presentation.screens.signup_screen

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.R
import com.example.gd.navigation.Screen
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorPrimary
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import kotlinx.coroutines.launch


@Composable
fun CreateAccountScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else colorPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
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
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    email = it
                })

            Spacer(modifier = Modifier.height(10.dp))

            var usermobilenumber by remember { mutableStateOf("") } // ВРЕМЕННО НЕДОСТУПНО
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
                shape = RoundedCornerShape(24.dp),
                onValueChange = {
                    password = it
                })
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator()
                }
                if (state.value?.isError?.isNotEmpty() == true) {
                    Text(
                        text = "Данные некорректны",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 20.sp
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }
                    //navController.navigate(Screen.OtpVerifyScreen.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorBlack),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Создать аккаунт",
                    color = colorWhite,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.LoginScreen.route)
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

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                        navController.navigate(Screen.HomeScreen.route)
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}

@Composable
@Preview
fun CreateAccountScreenPreview() {
    CreateAccountScreen(navController = NavController(LocalContext.current))
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CreateAccountScreenDarkPreview() {
    CreateAccountScreen(navController = NavController(LocalContext.current))

}
