package com.example.gd.domain.use_cases.OrderUseCases

import com.example.gd.domain.model.Product
import com.example.gd.domain.repositories.OrderRepository
import javax.inject.Inject

class ArrangeOrder @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(
        userid: String,
        products: List<Product>,
        counts: List<Int>,
        orderType: String = "",
        deliveryAddress: String = "",
        pointAddress: String = "",
        totalPrice: Double
    )
    =
        repository.arrangeOrder(userid, products, counts, orderType, deliveryAddress, pointAddress, totalPrice)
}