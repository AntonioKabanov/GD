package com.example.gd.presentation.Categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.model.Category
import com.example.gd.domain.model.User
import com.example.gd.domain.use_cases.AuthUseCases.AuthenticationUseCases
import com.example.gd.domain.use_cases.CategoryUseCases.CategoryUseCases
import com.example.gd.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
): ViewModel() {

    private val _getCategoryData = mutableStateOf<Response<List<Category>>>(Response.Loading)
    val getCategoryData: State<Response<List<Category>>> = _getCategoryData

    private val _addCategoryData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val addCategoryData: State<Response<Boolean>> = _addCategoryData

    private val _deleteCategoryData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val deleteCategoryData: State<Response<Boolean>> = _deleteCategoryData


    fun getCategoryList() {
        viewModelScope.launch {
            categoryUseCases.getCategoryList().collect {
                _getCategoryData.value = it
            }
        }
    }

    fun addNewCategory(name: String, categoryPhotoUrl: String) {
        viewModelScope.launch {
            categoryUseCases.addNewCategory(name, categoryPhotoUrl).collect {
                _addCategoryData.value = it
            }
        }
    }

    fun deleteNewCategory(name: String) {
        viewModelScope.launch {
            categoryUseCases.deleteCategory(name).collect {
                _deleteCategoryData.value = it
            }
        }
    }
}