package com.example.gd.domain.use_cases.UserUseCases

import com.example.gd.domain.repositories.UserRepository
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userid: String,
        userName: String,
        phone: String,
        deliveryAddress: String
    ) =
        repository.setUserDetails(
            userid = userid,
            userName = userName,
            phone = phone,
            deliveryAddress = deliveryAddress
        )
}