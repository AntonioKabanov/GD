package com.example.gd.domain.model

import com.example.gd.R

data class Product(
    val id: Int = 0,
    val ordersImageId: Int = R.drawable.burger,
    val name: String = "",
    val weight: String = "",
    val calories: String = "",
    val price: Double = 0.0,
)
