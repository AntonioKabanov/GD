package com.example.gd.domain.model

data class MyOrders(
    val id: Int,
    val ordersImageId: Int = 0,
    val name: String,
    val price: Double,
)
