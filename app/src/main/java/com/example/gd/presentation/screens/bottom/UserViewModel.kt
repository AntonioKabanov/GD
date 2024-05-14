package com.example.gd.presentation.screens.bottom

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.model.User
import com.example.gd.domain.use_cases.UserUseCases.UserUseCases
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userUseCases: UserUseCases
): ViewModel() {
    private val userid = firebaseAuth.currentUser?.uid

    private val _getUserData = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserData: State<Response<User?>> = _getUserData

    private val _setUserData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setUserData: State<Response<Boolean>> = _setUserData

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        if(userid != null) {
            viewModelScope.launch {
                userUseCases.getUserDetails(userid = userid).collect {
                    _getUserData.value = it
                }
            }
        }
    }
    fun setUserInfo(userName: String, phone: String, deliveryAddress: String) {
        if(userid != null) {
            viewModelScope.launch {
                userUseCases.setUserDetails(
                    userid = userid,
                    userName = userName,
                    phone = phone,
                    deliveryAddress = deliveryAddress
                ).collect {
                    _setUserData.value = it
                }
            }
        }
    }
}