package com.example.gd.domain.model

import com.example.gd.R
import java.io.Serializable

data class Product(
    var id: String = "",
    var image: String = "",
    var name: String = "",
    var weight: String = "",
    var calories: String = "",
    var price: Double = 0.0,
    var categoryId: String = ""
) : Serializable
