package com.example.gd.domain.use_cases.CategoryUseCases

data class CategoryUseCases(
    val addNewCategory: AddNewCategory,
    val deleteCategory: DeleteCategory,
    val getCategoryList: GetCategoryList
)
