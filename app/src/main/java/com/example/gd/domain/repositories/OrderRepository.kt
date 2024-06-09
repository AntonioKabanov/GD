package com.example.gd.domain.repositories

import com.example.gd.domain.model.Order
import com.example.gd.domain.model.Product
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOrderList(): Flow<Response<List<Order>>>
    suspend fun getOrderListByUser(userid: String): Flow<Response<List<Order>>>
    suspend fun arrangeOrder(
    userid: String,
        products: List<Product>,
        counts: List<Int>,
        orderType: String = "",
        deliveryAddress: String = "",
        pointAddress: String = "",
        totalPrice: Double
    ): Flow<Response<Boolean>>
    suspend fun acceptOrder(orderid: String, status: String): Flow<Response<Boolean>>
    suspend fun cancelOrder(orderid: String, status: String): Flow<Response<Boolean>>
}