package com.example.gd.presentation.Products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.model.Order
import com.example.gd.domain.model.Product
import com.example.gd.domain.use_cases.AuthUseCases.AuthenticationUseCases
import com.example.gd.domain.use_cases.OrderUseCases.OrderUseCases
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases,
    private val firebaseAuth: FirebaseAuth
): ViewModel()
{
    private val userid = firebaseAuth.currentUser?.uid

    private val _getOrderData = mutableStateOf<Response<List<Order>>>(Response.Loading)
    val getOrderData: State<Response<List<Order>>> = _getOrderData

    private val _arrangeOrderData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val arrangeOrderData: State<Response<Boolean>> = _arrangeOrderData

    private val _acceptOrderData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val acceptOrderData: State<Response<Boolean>> = _acceptOrderData

    private val _cancelOrderData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val cancelOrderData: State<Response<Boolean>> = _cancelOrderData

    fun arrangeOrder(
        products: List<Product>,
        counts: List<Int>,
        orderType: String,
        deliveryAddress: String,
        pointAddress: String,
        totalPrice: Double
    ) {
        if (userid != null) {
            viewModelScope.launch {
                orderUseCases.arrangeOrder(
                    userid, products, counts, orderType, deliveryAddress, pointAddress ,totalPrice
                ).collect {
                    _arrangeOrderData.value = it
                }
            }
        }
    }

    fun getOrderList() {
        if (userid != null) {
            viewModelScope.launch {
                orderUseCases.getOrderList().collect {
                    _getOrderData.value = it
                }
            }
        }
    }

    fun getOrderListByUser() {
        if (userid != null) {
            viewModelScope.launch {
                orderUseCases.getOrderListByUser(userid).collect {
                    _getOrderData.value = it
                }
            }
        }
    }

    fun acceptOrder(orderid: String, status: String) {
        viewModelScope.launch {
            orderUseCases.acceptOrder(orderid, status).collect {
                _acceptOrderData.value = it
            }
        }
    }

    fun cancelOrder(orderid: String, status: String) {
        viewModelScope.launch {
            orderUseCases.cancelOrder(orderid, status).collect {
                _cancelOrderData.value = it
            }
        }
    }
}