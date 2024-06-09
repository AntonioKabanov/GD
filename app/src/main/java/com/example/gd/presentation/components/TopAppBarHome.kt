package com.example.gd.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.DashboardCustomize
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.domain.model.Point
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.presentation.Products.PointViewModel
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response

@Composable
fun TopAppBarHome(
    pointViewModel: PointViewModel = hiltViewModel(),
    onDashboardClick: () -> Unit
) {
    var pointList by remember { mutableStateOf(emptyList<Point>()) }
    var userselectaddress by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    pointViewModel.getPointList()
    pointViewModel.getPointByUser()

    when (val response = pointViewModel.getPointData.value) {
        is Response.Loading -> {}
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if (response.data != null) {
                pointList = response.data
            }
        }
    }

    when (val response = pointViewModel.getCurrentPointData.value) {
        is Response.Loading -> {}
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if (response.data != null) {
                userselectaddress = "${response.data.pointCity}, ${response.data.pointAddress}"
            }
        }
    }

    when (val response = pointViewModel.setPointData.value) {
        is Response.Loading -> {}
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if (response.data) {
                Toast(message = "Была выбрана новая точка")
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(colorWhite)
        ) {
            IconButton(onClick = onDashboardClick) {
                Icon(
                    imageVector = Icons.Outlined.Dashboard,
                    contentDescription = "",
                    tint = colorBlack
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(0.85f)
                .padding(start = 20.dp, end = 20.dp)
        ) {
            TextField(
                value = userselectaddress,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorWhite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Выберите точку",
                        color = colorBlack
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = "",
                        tint = colorBlack,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                readOnly = true
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                pointList.forEach { point ->
                    DropdownMenuItem(onClick = {
                        pointViewModel.setPoint(point)
                        userselectaddress = "${point.pointCity}, ${point.pointAddress}"
                        expanded = false
                    }) {
                        Text(text = "${point.pointCity}, ${point.pointAddress}")
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(colorWhite)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "",
                    tint = colorBlack
                )
            }
        }
    }
}

