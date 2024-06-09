package com.example.gd.domain.use_cases.OrderUseCases

import com.example.gd.domain.repositories.OrderRepository
import javax.inject.Inject

class CancelOrder @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderid: String, status: String) = repository.cancelOrder(orderid, status)
}