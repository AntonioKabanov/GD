package com.example.gd.presentation.Products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.model.Point
import com.example.gd.domain.model.Product
import com.example.gd.domain.use_cases.PointUseCases.PointUseCases
import com.example.gd.domain.use_cases.ProductUseCases.ProductUseCases
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointViewModel @Inject constructor(
    private val pointUseCases: PointUseCases,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val userid = firebaseAuth.currentUser?.uid

    private val _getPointData = mutableStateOf<Response<List<Point>>>(Response.Loading)
    val getPointData: State<Response<List<Point>>> = _getPointData

    private val _setPointData = mutableStateOf<Response<Boolean>>(Response.Loading)
    val setPointData: State<Response<Boolean>> = _setPointData

    private val _getCurrentPointData = mutableStateOf<Response<Point>>(Response.Loading)
    val getCurrentPointData: State<Response<Point>> = _getCurrentPointData


    fun getPointList() {
        viewModelScope.launch {
            pointUseCases.getPointList().collect {
                _getPointData.value = it
            }
        }
    }

    fun setPoint(point: Point) {
        if (userid != null) {
            viewModelScope.launch {
                pointUseCases.setPoint(userid, point).collect {
                    _setPointData.value = it
                }
            }
        }
    }

    fun getPointByUser() {
        if (userid != null) {
            viewModelScope.launch {
                pointUseCases.getPoint(userid).collect {
                    _getCurrentPointData.value = it
                }
            }
        }
    }
}