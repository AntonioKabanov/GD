package com.example.gd.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gd.R
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorGray

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    imageResource: Int,
    contentDescription: String?,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Icon(
            painter = painterResource(id = imageResource),
            contentDescription = contentDescription,
            tint = Color.DarkGray,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.W300
            ),
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileItemPreview() {
    ProfileItem(
        imageResource = R.drawable.baseline_phone_24,
        contentDescription = null,
        text = "+79108256544"
    )
}