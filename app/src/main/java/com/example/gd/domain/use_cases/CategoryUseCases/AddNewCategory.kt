package com.example.gd.domain.use_cases.CategoryUseCases

import com.example.gd.domain.repositories.CategoryRepository
import javax.inject.Inject

class AddNewCategory @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(
        name: String,
        categoryPhotoUrl: String = ""
    ) =
        repository.addNewCategory(
            name = name,
            categoryPhotoUrl = categoryPhotoUrl
        )
}