package com.example.gd.presentation.Support

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gd.domain.model.Point
import com.example.gd.domain.model.SupportMessage
import com.example.gd.domain.use_cases.PointUseCases.PointUseCases
import com.example.gd.domain.use_cases.SupportUseCases.SupportUseCases
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor(
    private val supportUseCases: SupportUseCases,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val userid = firebaseAuth.currentUser?.uid

    private val _getAllMessagesData = mutableStateOf<Response<List<SupportMessage>>>(Response.Loading)
    val getAllMessagesData: State<Response<List<SupportMessage>>> = _getAllMessagesData

    private val _getUserMessagesData = mutableStateOf<Response<List<SupportMessage>>>(Response.Loading)
    val getUserMessagesData: State<Response<List<SupportMessage>>> = _getUserMessagesData

    private val _sendMessageData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val sendMessageData: State<Response<Boolean>> = _sendMessageData

    private val _sendAnswerData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val sendAnswerData: State<Response<Boolean>> = _sendAnswerData


    fun getAllMessages() {
        viewModelScope.launch {
            supportUseCases.getAllMessages().collect {
                _getAllMessagesData.value = it
            }
        }
    }

    fun getUserMessages() {
        if (userid != null) {
            viewModelScope.launch {
                supportUseCases.getUserMessages(userid).collect {
                    _getUserMessagesData.value = it
                }
            }
        }
    }
    fun sendMessage(
        header: String,
        question: String
    ) {
        if (userid != null) {
            viewModelScope.launch {
                supportUseCases.sendMessage(userid, header, question).collect {
                    _sendMessageData.value = it
                }
            }
        }
    }

    fun sendAnswer(
        messageid: String,
        answer: String
    ) {
        if (userid != null) {
            viewModelScope.launch {
                supportUseCases.sendAnswer(messageid, answer).collect {
                    _sendAnswerData.value = it
                }
            }
        }
    }

}