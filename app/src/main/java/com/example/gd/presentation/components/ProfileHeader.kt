package com.example.gd.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gd.R
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.util.Constants

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    imageResource: Int,
    contentDescription: String?,
    userName: String,
    userRole: String
) {
    val roleColor = when (userRole) {
        Constants.ROLE_ADMIN -> {
            Color.Red
        }
        Constants.ROLE_MANAGER -> {
            Color.Blue
        }
        else -> {
            Color.Gray
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(top = 12.dp)
                .size(128.dp)
                .clip(CircleShape)
                .border(1.dp, colorRedDark, CircleShape),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userName,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.W500
                ),
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "($userRole)",
                style = TextStyle(
                    fontSize = 17.sp,
                    color = roleColor,
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader(
        imageResource = R.drawable.ic_launcher_background,
        contentDescription = null,
        userName = "Nikolay Bistrow",
        userRole = Constants.ROLE_MANAGER
    )
}