package com.example.gd.presentation.Authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.use_cases.AuthUseCases.AuthenticationUseCases
import com.example.gd.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authUseCases: AuthenticationUseCases
): ViewModel(){
    val isUserAuthenticated get() = authUseCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState: State<Response<Boolean>> = _signOutState

    private val _deleteUserState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val deleteUserState: State<Response<Boolean>> = _deleteUserState

    private val _firebaseAuthState = mutableStateOf<Boolean>(false)
    val firebaseAuthState: State<Boolean> = _firebaseAuthState

    fun signOut() {
        viewModelScope.launch {
            authUseCases.firebaseSignOut().collect {
                _signOutState.value = it
                if (it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                    _signUpState.value = Response.Success(false)
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignIn(email, password).collect {
                _signInState.value = it
                if (it == Response.Success(true)) {
                    _signOutState.value = Response.Success(false)
                    _deleteUserState.value = Response.Success(false)
                }
            }
        }
    }

    fun signUp(email: String, password: String, userName: String) {
        viewModelScope.launch {
            authUseCases.firebaseSignUp(email, password, userName).collect {
                _signUpState.value = it
                if (it == Response.Success(true)) {
                    _signInState.value = Response.Success(true)
                    _signOutState.value = Response.Success(false)
                    _deleteUserState.value = Response.Success(false)
                }
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            authUseCases.firebaseDeleteUser().collect {
                _deleteUserState.value = it
                if(it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                    _signUpState.value = Response.Success(false)
                    _signOutState.value = Response.Success(true)
                }
            }
        }
    }

    fun getFirebaseAuthState() {
        viewModelScope.launch {
            authUseCases.firebaseAuthState().collect {
                _firebaseAuthState.value = it
            }
        }
    }
}