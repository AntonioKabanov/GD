package com.example.gd.presentation.screens.admin_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gd.presentation.navigation.Screen
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.components.TopAppBarAdmin
import com.example.gd.ui.theme.colorWhite

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminPanelScreen(
    navController: NavController,
    adminViewModel: AdminViewModel
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        TopAppBarAdmin(
            headerName = "Админ-панель",
            onBackClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TextButton(
            onClick = {
                navController.navigate(Screen.CategoryManageScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Управление категориями",
                style = MaterialTheme.typography.button,
                color = colorWhite
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(
            onClick = {
                navController.navigate(Screen.ProductManageScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Управление позициями меню",
                style = MaterialTheme.typography.button,
                color = colorWhite
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(
            onClick = {
                navController.navigate(Screen.UserManageScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Управление пользователями",
                style = MaterialTheme.typography.button,
                color = colorWhite
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(
            onClick = {
                navController.navigate(Screen.SupportScreenAdmin.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red, RoundedCornerShape(10.dp))
        ) {
            Text(
                text = "Тех. поддержка",
                style = MaterialTheme.typography.button,
                color = colorWhite
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Preview
@Composable
fun AdminPanelScreenPreview() {

}