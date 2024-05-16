package com.example.gd.presentation.screens.admin_panel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.use_cases.AdminUseCases.AdminUseCases
import com.example.gd.domain.use_cases.AuthUseCases.AuthenticationUseCases
import com.example.gd.domain.use_cases.UserUseCases.UserUseCases
import com.example.gd.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminViewModel @Inject constructor(
    private val authUseCases: AuthenticationUseCases,
    private val adminUseCases: AdminUseCases
): ViewModel() {

    private val _addNewUserState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val addNewUserState: State<Response<Boolean>> = _addNewUserState

    private val _editAnotherUserState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val editAnotherUserState: State<Response<Boolean>> = _editAnotherUserState

    private val _deleteAnotherUserState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val deleteAnotherUserState: State<Response<Boolean>> = _deleteAnotherUserState

    fun addNewUser(email: String, password: String, userName: String, userRole: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignUp(email, password, userName, userRole).collect {
                _addNewUserState.value = it
            }
        }
    }

    fun deleteAnotherUser() {
        viewModelScope.launch {
            authUseCases.firebaseDeleteUser().collect {
                _deleteAnotherUserState.value = it
            }
        }
    }

    fun editAnotherUser(
        email: String,
        userName: String,
        userRole: String,
        phone: String,
        deliveryAddress: String
    ) {
        viewModelScope.launch {
            adminUseCases.editAnotherUser(
                email = email,
                userName = userName,
                userRole = userRole,
                phone = phone,
                deliveryAddress = deliveryAddress
            ).collect {
                _editAnotherUserState.value = it
            }
        }
    }
}