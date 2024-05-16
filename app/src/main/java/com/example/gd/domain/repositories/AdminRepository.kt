package com.example.gd.domain.repositories

import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
   suspend fun editAnotherUser(
        email: String,
        userName: String,
        userRole: String,
        phone: String,
        deliveryAddress: String
   ): Flow<Response<Boolean>>
}