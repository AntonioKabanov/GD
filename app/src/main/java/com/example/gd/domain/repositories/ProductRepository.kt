package com.example.gd.domain.repositories

import com.example.gd.domain.model.Product
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductList(): Flow<Response<List<Product>>>
    suspend fun addProduct(
        image: String,
        name: String,
        weight: String,
        calories: String,
        price: Double,
        categoryName: String
    ): Flow<Response<Boolean>>
    suspend fun getFavoriteById(userid: String): Flow<Response<List<Product>>>
    suspend fun getOrderById(userid: String): Flow<Response<List<Product>>>
    suspend fun getProductsByCategory(categoryid: String): Flow<Response<List<Product>>>
    suspend fun addProductInFavorite(productid: String, userid: String): Flow<Response<Boolean>>
    suspend fun addProductInOrder(productid: String, userid: String): Flow<Response<Boolean>>
    suspend fun deleteProduct(productName: String): Flow<Response<Boolean>>
    suspend fun deleteFromOrder(productid: String, userid: String): Flow<Response<Boolean>>
    suspend fun deleteFromFavorite(productid: String, userid: String): Flow<Response<Boolean>>

}