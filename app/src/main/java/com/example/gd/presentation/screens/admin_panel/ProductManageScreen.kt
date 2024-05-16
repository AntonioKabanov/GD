package com.example.gd.presentation.screens.admin_panel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.components.ManageButtonsComponent
import com.example.gd.presentation.components.TopAppBarAdmin

@Composable
fun ProductManageScreen(
    navController: NavController,
    adminViewModel: AdminViewModel
) {
    var menuManageShow by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        TopAppBarAdmin(
            headerName = "Позиции меню",
            onBackClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(bottom = 10.dp)
        )
        ManageButtonsComponent(
            onAddClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}