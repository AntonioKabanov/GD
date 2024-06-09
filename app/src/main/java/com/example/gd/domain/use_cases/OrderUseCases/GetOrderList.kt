package com.example.gd.domain.use_cases.OrderUseCases

import com.example.gd.domain.repositories.OrderRepository
import javax.inject.Inject

class GetOrderList @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke() = repository.getOrderList()
}