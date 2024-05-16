package com.example.gd.domain.use_cases.CategoryUseCases

import com.example.gd.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoryList @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke() = repository.getCategoryList()
}