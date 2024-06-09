package com.example.gd.domain.use_cases.ProductUseCases

import com.example.gd.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductList @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.getProductList()
}