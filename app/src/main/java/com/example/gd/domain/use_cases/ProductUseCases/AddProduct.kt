package com.example.gd.domain.use_cases.ProductUseCases

import com.example.gd.R
import com.example.gd.domain.repositories.ProductRepository
import javax.inject.Inject

class AddProduct @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        image: String,
        name: String,
        weight: String,
        calories: String,
        price: Double,
        categoryName: String
    ) = repository.addProduct(image, name, weight, calories, price, categoryName)
}