package com.example.gd.domain.repositories

import com.example.gd.domain.model.Product
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductList(): Flow<Response<Product>>
    suspend fun addProduct(
        id: Int,
        ordersImageId: String,
        name: String,
        price: Double
    ): Flow<Response<Boolean>>
    suspend fun addProductInFavorite(productid: String): Flow<Response<Boolean>>
    suspend fun addProductInOrder(productid: String): Flow<Response<Boolean>>

}