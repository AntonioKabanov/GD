package com.example.gd.presentation.screens.bottom

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gd.presentation.screens.login_screen.SignInState
import com.example.gd.presentation.screens.login_screen.SignInViewModel
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorRedLite
import com.example.gd.ui.theme.colorWhite
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val state = viewModel.userState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val db = Firebase.database

    Log.d("TEST", state.value?.uid.toString())
    Log.d("TEST", state.value?.email.toString())
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Black else colorWhite)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = state.value.) {

            }
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.h5,
                color = colorBlack,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "ID:",
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 10.dp)
            )
            Text(
                text = "Имя:",
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 10.dp)
            )
            Text(
                text = "Телефон:",
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 10.dp)
            )
            Text(
                text = "EMail: ${state.value!!.email}",
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 10.dp)
            )
            Text(
                text = "Дата регистрации:",
                style = MaterialTheme.typography.h6,
                color = colorBlack,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .background(colorRedDark, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Редактировать",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .background(colorRedDark, RoundedCornerShape(10.dp))
                ) {
                    Text(
                        "Выйти из аккаунта",
                        style = TextStyle(
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview() {
    ProfileScreen(navController = NavController(LocalContext.current))
}


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfileScreenDarkPreview() {
    ProfileScreen(navController = NavController(LocalContext.current))
}