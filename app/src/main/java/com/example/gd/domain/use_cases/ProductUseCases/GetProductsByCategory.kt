package com.example.gd.domain.use_cases.ProductUseCases

import com.example.gd.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductsByCategory @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(categoryid: String) = repository.getProductsByCategory(categoryid)
}