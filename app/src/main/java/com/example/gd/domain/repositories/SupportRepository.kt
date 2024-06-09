package com.example.gd.domain.repositories

import com.example.gd.domain.model.SupportMessage
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface SupportRepository {
    suspend fun getAllMessages(): Flow<Response<List<SupportMessage>>>
    suspend fun getUserMessages(userid: String): Flow<Response<List<SupportMessage>>>
    suspend fun sendMessage(userid: String, header: String, question: String): Flow<Response<Boolean>>
    suspend fun sendAnswer(messageid: String, answer: String): Flow<Response<Boolean>>
}