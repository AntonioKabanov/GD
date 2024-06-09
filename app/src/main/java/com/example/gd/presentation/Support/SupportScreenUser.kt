package com.example.gd.presentation.Support

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gd.domain.model.SupportMessage
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response

@Composable
fun SupportScreenUser(
    navController: NavController,
    supportViewModel: SupportViewModel = hiltViewModel()
) {
    var userMessages by remember { mutableStateOf(emptyList<SupportMessage>()) }
    supportViewModel.getUserMessages()
    when (val response = supportViewModel.getUserMessagesData.value) {
        is Response.Loading -> {

        }
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if (response.data != null) {
                userMessages = response.data
            }
        }
    }

    var header by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorWhite)
            .padding(16.dp)
    ) {
        Text(
            text = "Техническая поддержка",
            style = MaterialTheme.typography.h6,
            color = colorRedDark,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = header,
            onValueChange = { header = it },
            label = { Text("Заголовок") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Сообщение") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                if (header.isNotEmpty() && message.isNotEmpty()) {
                    supportViewModel.sendMessage(
                        header = header,
                        question = message
                    )
                    header = ""
                    message = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Filled.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Отправить")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(userMessages) { supportMessage ->
                MessageCard(supportMessage)
                Divider(color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun MessageCard(supportMessage: SupportMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorWhite)
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = supportMessage.header,
            fontWeight = FontWeight.Bold,
            color = colorRedDark,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = supportMessage.question,
            color = Color.Black,
            style = MaterialTheme.typography.body1
        )
        if (supportMessage.answer.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ответ администратора:",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = supportMessage.answer,
                color = Color.Black,
                style = MaterialTheme.typography.body1
            )
        }
    }
}