package com.example.gd.domain.repositories

import com.example.gd.util.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean
    fun getFirebaseAuthState(): Flow<Boolean>
    suspend fun loginUser(email: String, password: String): Flow<Response<Boolean>>
    suspend fun registerUser(email: String, password: String, userName: String): Flow<Response<Boolean>>
    suspend fun signOutUser(): Flow<Response<Boolean>>
}