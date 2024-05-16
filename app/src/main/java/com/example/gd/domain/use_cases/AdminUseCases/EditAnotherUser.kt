package com.example.gd.domain.use_cases.AdminUseCases

import com.example.gd.domain.repositories.AdminRepository
import javax.inject.Inject

class EditAnotherUser @Inject constructor(
    private val repository: AdminRepository
) {
    suspend operator fun invoke(
        email: String,
        userName: String,
        userRole: String,
        phone: String,
        deliveryAddress: String
    ) =
        repository.editAnotherUser(
            email = email,
            userName = userName,
            userRole = userRole,
            phone = phone,
            deliveryAddress = deliveryAddress
        )
}