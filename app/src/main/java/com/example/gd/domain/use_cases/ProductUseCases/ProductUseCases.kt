package com.example.gd.domain.use_cases.ProductUseCases

data class ProductUseCases(
    val addProduct: AddProduct,
    val addProductInFavorite: AddProductInFavorite,
    val addProductInOrder: AddProductInOrder,
    val deleteProduct: DeleteProduct,
    val getFavoriteById: GetFavoriteById,
    val getOrderById: GetOrderById,
    val getProductList: GetProductList,
    val getProductsByCategory: GetProductsByCategory,
    val deleteFromOrder: DeleteFromOrder
)
