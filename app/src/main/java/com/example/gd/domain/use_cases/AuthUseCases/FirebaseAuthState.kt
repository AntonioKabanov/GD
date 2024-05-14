package com.example.gd.domain.use_cases.AuthUseCases

import com.example.gd.domain.repositories.AuthRepository
import javax.inject.Inject

class FirebaseAuthState @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getFirebaseAuthState()
}