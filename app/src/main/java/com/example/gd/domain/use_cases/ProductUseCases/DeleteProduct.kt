package com.example.gd.domain.use_cases.ProductUseCases

import com.example.gd.domain.repositories.ProductRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productName: String) = repository.deleteProduct(productName)
}