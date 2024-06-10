package com.example.gd.presentation.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.gd.R
import com.example.gd.ui.theme.colorBlack
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.util.Constants

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    imageResource: Uri?,
    contentDescription: String?,
    userName: String,
    userRole: String,
    onPhotoClick: () -> Unit
) {
    val roleColor = when (userRole) {
        Constants.ROLE_ADMIN -> Color.Red
        Constants.ROLE_MANAGER -> Color.Blue
        else -> Color.Gray
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        imageResource?.let {
            Log.d("ProfileHeader", "Image URI: $it")
        }

        if (imageResource != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageResource)
                    .crossfade(true)
                    .listener(
                        onStart = { Log.d("Coil", "Loading started with URI: $imageResource") },
                        onSuccess = { _, _ -> Log.d("Coil", "Loading successful with URI: $imageResource") },
                        onError = { _, _ -> Log.e("Coil", "Loading failed with URI: $imageResource") }
                    )
                    .build(),
                contentDescription = "Uploaded Photo",
                modifier = Modifier
                    .padding(top = 12.dp)
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(1.dp, colorRedDark, CircleShape)
                    .clickable { onPhotoClick() },
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = contentDescription,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(1.dp, colorRedDark, CircleShape)
                    .clickable { onPhotoClick() },
                contentScale = ContentScale.Crop
            )
        }

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




/*
@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader(
        imageResource = null,
        contentDescription = null,
        userName = "Nikolay Bistrow",
        userRole = Constants.ROLE_MANAGER
    )
}*/
