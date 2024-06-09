package com.example.gd.domain.use_cases.SupportUseCases

import com.example.gd.domain.repositories.SupportRepository
import javax.inject.Inject

class GetUserMessages @Inject constructor(
    private val repository: SupportRepository
) {
    suspend operator fun invoke(userid: String) = repository.getUserMessages(userid)
}