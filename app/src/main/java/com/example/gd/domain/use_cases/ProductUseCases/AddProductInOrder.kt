package com.example.gd.domain.use_cases.ProductUseCases

import com.example.gd.domain.repositories.ProductRepository
import javax.inject.Inject

class AddProductInOrder @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(productid: String, userid: String) = repository.addProductInOrder(productid, userid)
}