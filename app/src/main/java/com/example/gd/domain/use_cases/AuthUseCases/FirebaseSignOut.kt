package com.example.gd.domain.use_cases.AuthUseCases

import com.example.gd.domain.repositories.AuthRepository
import javax.inject.Inject

class FirebaseSignOut @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.signOutUser()
}