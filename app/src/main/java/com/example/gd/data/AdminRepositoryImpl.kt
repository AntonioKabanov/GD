package com.example.gd.data

import android.util.Log
import com.example.gd.domain.repositories.AdminRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): AdminRepository {
    private var operationSuccessful = false
    override suspend fun editAnotherUser(
        email: String,
        userName: String,
        userRole: String,
        phone: String,
        deliveryAddress: String
    ): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        operationSuccessful = false
        var userid = ""
        try {
            val userObj = mutableMapOf<String, String>()
            userObj["userName"] = userName
            userObj["phone"] = phone
            userObj["deliveryAddress"] = deliveryAddress
            userObj["role"] = userRole

            database.collection(Constants.COLLECTION_NAME_USERS)
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener {
                    userid = it.documents.first().id
                }.addOnFailureListener {
                    Log.d("ERROR", it.localizedMessage.toString())
                }.await()

            database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .update(userObj as Map<String, Any>)
                .addOnSuccessListener { operationSuccessful = true }.await()

            if(operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            }
            else {
                emit(Response.Error("Ошибка при обновлении данных пользователя"))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }
}