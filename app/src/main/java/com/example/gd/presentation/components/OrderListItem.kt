package com.example.gd.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gd.util.Constants

@Composable
fun OrderListItem(
    orderId: String,
    userName: String,
    productName: String,
    productCount: String,
    orderType: String,
    deliveryAddress: String? = null,
    status: String,
    onAcceptClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = orderId.take(4)
        )
        Text(
            text = userName
        )
        Text(
            text = productName
        )
        Text(
            text = productCount
        )
        Text(
            text = orderType
        )
        if (deliveryAddress != null) {
            Text(
                text = deliveryAddress
            )
        }
        Text(
            text = status
        )
        Button(onClick = { /*TODO*/ }) {

        }
        Button(onClick = { /*TODO*/ }) {

        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderListItemPreview() {
    OrderListItem(
        orderId = "dsad",
        userName = "Ivan",
        productName = "Шаверма",
        productCount = "2",
        orderType = Constants.GET_IN_POINT,
        status = Constants.ORDER_CREATED,
        onAcceptClick = {},
        onCancelClick = {}
    )
}