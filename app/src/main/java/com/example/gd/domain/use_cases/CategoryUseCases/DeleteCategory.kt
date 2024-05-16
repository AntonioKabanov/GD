package com.example.gd.domain.use_cases.CategoryUseCases

import com.example.gd.domain.repositories.CategoryRepository
import javax.inject.Inject

class DeleteCategory @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(name: String) = repository.deleteCategory(name)
}