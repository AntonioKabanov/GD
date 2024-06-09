package com.example.gd.domain.use_cases.SupportUseCases

import com.example.gd.domain.repositories.SupportRepository
import javax.inject.Inject

class GetAllMessages @Inject constructor(
    private val repository: SupportRepository
) {
    suspend operator fun invoke() = repository.getAllMessages()
}