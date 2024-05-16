package com.example.gd.domain.use_cases.AuthUseCases

import com.example.gd.domain.repositories.AuthRepository
import com.example.gd.util.Constants
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        userName: String,
        userRole: String = ""
    ) =
        repository.registerUser(email, password, userName, userRole)
}