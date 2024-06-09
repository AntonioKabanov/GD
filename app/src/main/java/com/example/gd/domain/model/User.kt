package com.example.gd.domain.model

data class User(
    var userid: String = "",
    var userName: String = "",
    var role: String = "",
    var email: String = "",
    var password: String = "",
    var imageUrl: String = "",
    var phone: String = "",
    var deliveryAddress: String = "",
    var registrationDate: String = "",
    var loyaltyPoints: Int = 0,
    var selectedPoint: String = "",
    var favorites: List<String> = emptyList(),
    var orderList: List<String> = emptyList()
)
