package com.example.gd.domain.use_cases.OrderUseCases

import com.example.gd.domain.repositories.OrderRepository
import javax.inject.Inject

class GetOrderListByUser @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(userid: String) = repository.getOrderListByUser(userid)
}