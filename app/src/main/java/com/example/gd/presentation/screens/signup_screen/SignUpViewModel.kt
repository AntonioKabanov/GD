package com.example.gd.presentation.screens.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.data.repositories.AuthRepository
import com.example.gd.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()


    fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect {result ->
            when(result) {
                is Resource.Success -> {
                    _signUpState.send(SignUpState(isSuccess = "Sign In Success"))
                }
                is Resource.Loading -> {
                    _signUpState.send(SignUpState(isLoading = true))
                }
                is Resource.Error -> {
                    _signUpState.send(SignUpState(isError = result.message))
                }
            }
        }
    }
}