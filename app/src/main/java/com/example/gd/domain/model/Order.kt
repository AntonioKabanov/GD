package com.example.gd.domain.model

import com.example.gd.util.Constants
import com.google.firebase.Timestamp
import java.io.Serializable

data class Order(
    var id: String = "",
    var userid: String = "",
    var productNames: List<String> = emptyList(),
    var productCounts: List<Int> = emptyList(),
    var orderType: String = Constants.GET_IN_POINT,
    var deliveryAddress: String = "",
    var pointAddress: String = "",
    var status: String = Constants.ORDER_CREATED,
    var timestamp: Timestamp = Timestamp.now(),
    var createdAt: String = "",
    var totalPrice: Double = 0.0
) : Serializable
