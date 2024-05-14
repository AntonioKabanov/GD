package com.example.gd.domain.repositories

import com.example.gd.domain.model.Category
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategoryList(): Flow<Response<Category>>
}