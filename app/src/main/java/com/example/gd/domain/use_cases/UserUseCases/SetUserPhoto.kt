package com.example.gd.domain.use_cases.UserUseCases

import com.example.gd.domain.repositories.UserRepository
import javax.inject.Inject

class SetUserPhoto @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userid: String,
        photo: String,
    ) =
        repository.setUserPhoto(userid, photo)
}