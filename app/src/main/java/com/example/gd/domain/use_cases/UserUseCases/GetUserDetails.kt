package com.example.gd.domain.use_cases.UserUseCases

import com.example.gd.domain.repositories.UserRepository
import java.util.concurrent.Flow
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(userid: String) =
        repository.getUserDetails(userid = userid)
}