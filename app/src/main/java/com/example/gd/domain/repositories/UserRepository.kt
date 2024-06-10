package com.example.gd.domain.repositories

import com.example.gd.domain.model.User
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserDetails(userid: String): Flow<Response<User>>
    suspend fun setUserDetails(
        userid: String,
        userName: String,
        phone: String,
        deliveryAddress: String,
    ) : Flow<Response<Boolean>>
    suspend fun setUserPhoto(userid: String, photo: String): Flow<Response<Boolean>>
}