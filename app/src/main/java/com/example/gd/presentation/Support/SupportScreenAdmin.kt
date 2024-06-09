package com.example.gd.presentation.Support

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.example.gd.domain.model.SupportMessage
import com.example.gd.presentation.Authentication.Toast
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorWhite
import com.example.gd.util.Response

@Composable
fun SupportScreenAdmin(supportViewModel: SupportViewModel = hiltViewModel()) {
    var allMessages by remember { mutableStateOf(emptyList<SupportMessage>()) }
    var response by remember { mutableStateOf("") }
    var selectedMessage by remember { mutableStateOf<SupportMessage?>(null) }

    supportViewModel.getAllMessages()
    when (val response = supportViewModel.getAllMessagesData.value) {
        is Response.Loading -> {
        }
        is Response.Error -> {
            Toast(message = response.message)
        }
        is Response.Success -> {
            if (response.data != null) {
                allMessages = response.data
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorWhite)
            .padding(16.dp)
    ) {
        Text(
            text = "Админ. панель поддержки",
            style = MaterialTheme.typography.h6,
            color = colorRedDark,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        selectedMessage?.let { message ->
            OutlinedTextField(
                value = response,
                onValueChange = { response = it },
                label = { Text("Ответ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Button(
                onClick = {
                    if (response.isNotEmpty()) {
                        supportViewModel.sendAnswer(message.id, response)
                        response = ""
                        selectedMessage = null
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
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(allMessages) { supportMessage ->
                MessageCardAdmin(
                    supportMessage = supportMessage,
                    onSelectMessage = { selectedMessage = it }
                )
                Divider(color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun MessageCardAdmin(
    supportMessage: SupportMessage,
    onSelectMessage: (SupportMessage) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorWhite)
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
            .clickable { onSelectMessage(supportMessage) }
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
        Text(
            text = "От: ${supportMessage.userid.take(4)}",
            color = Color.Gray,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(top = 4.dp)
        )
        supportMessage.answer.let {
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
                text = it,
                color = Color.Black,
                style = MaterialTheme.typography.body1
            )
        }
    }
}