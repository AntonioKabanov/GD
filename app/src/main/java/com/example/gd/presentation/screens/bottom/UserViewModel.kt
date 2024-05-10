package com.example.gd.presentation.screens.bottom

import androidx.lifecycle.ViewModel
import com.example.gd.data.repositories.AuthRepository
import com.example.gd.domain.model.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _userState = Channel<User>()
    val userState = _userState.receiveAsFlow()

    // ПОД БД
    /*fun getUserData() = viewModelScope.launch {
        repository.getUserData().collect {
            return@collect
        }
    }*/
}