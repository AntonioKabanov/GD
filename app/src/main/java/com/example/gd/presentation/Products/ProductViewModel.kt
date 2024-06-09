package com.example.gd.presentation.Products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.model.Category
import com.example.gd.domain.model.Product
import com.example.gd.domain.use_cases.CategoryUseCases.CategoryUseCases
import com.example.gd.domain.use_cases.ProductUseCases.ProductUseCases
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val userid = firebaseAuth.currentUser?.uid

    private val _getProductData = mutableStateOf<Response<List<Product>>>(Response.Loading)
    val getProductData: State<Response<List<Product>>> = _getProductData

    private val _getOrderData = mutableStateOf<Response<List<Product>>>(Response.Loading)
    val getOrderData: State<Response<List<Product>>> = _getOrderData

    private val _addProductData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val addProductData: State<Response<Boolean>> = _addProductData

    private val _addProductInOrderData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val addProductInOrderData: State<Response<Boolean>> = _addProductInOrderData

    private val _deleteProductData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val deleteProductData: State<Response<Boolean>> = _deleteProductData

    private val _deleteFromOrderData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val deleteFromOrderData: State<Response<Boolean>> = _deleteFromOrderData


    fun getProductList() {
        viewModelScope.launch {
            productUseCases.getProductList().collect {
                _getProductData.value = it
            }
        }
    }

    fun getProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            productUseCases.getProductsByCategory(categoryId).collect {
                _getProductData.value = it
            }
        }
    }

    fun getOrderById() {
        if (userid != null) {
            viewModelScope.launch {
                productUseCases.getOrderById(userid = userid).collect {
                    _getOrderData.value = it
                }
            }
        }
    }

    fun addProduct(
        image: String,
        name: String,
        weight: String,
        calories: String,
        price: Double,
        categoryName: String
    ) {
        viewModelScope.launch {
            productUseCases.addProduct(
                image = image,
                name = name,
                weight = weight,
                calories = calories,
                price = price,
                categoryName = categoryName
            ).collect {
                _addProductData.value = it
            }
        }
    }

    fun addProductInOrder(productId: String) {
        if (userid != null) {
            viewModelScope.launch {
                productUseCases.addProductInOrder(productid = productId, userid = userid).collect {
                    _addProductInOrderData.value = it
                }
            }
        }
    }

    fun deleteProduct(productName: String) {
        viewModelScope.launch {
            productUseCases.deleteProduct(productName).collect {
                _deleteProductData.value = it
            }
        }
    }

    fun deleteFromOrder(productId: String) {
        if(userid != null) {
            viewModelScope.launch {
                productUseCases.deleteFromOrder(productId, userid).collect {
                    _deleteFromOrderData.value = it
                }
            }
        }

    }
}