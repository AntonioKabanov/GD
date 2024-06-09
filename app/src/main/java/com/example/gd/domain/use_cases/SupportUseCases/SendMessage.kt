package com.example.gd.domain.use_cases.SupportUseCases

import com.example.gd.domain.repositories.SupportRepository
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val repository: SupportRepository
) {
    suspend operator fun invoke(userid: String, header: String, question: String) = repository.sendMessage(userid, header, question)
}