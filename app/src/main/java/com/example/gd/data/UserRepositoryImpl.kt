package com.example.gd.data

import com.example.gd.domain.model.User
import com.example.gd.domain.repositories.UserRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): UserRepository {
    private var operationSuccessul = false
    override suspend fun getUserDetails(userid: String): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_USERS)
            .document(userid)
            .addSnapshotListener {snapshot, error ->
                val response = if(snapshot != null) {
                    val userInfo = snapshot.toObject(User::class.java)
                    Response.Success<User>(userInfo!!)
                }
                else {
                    Response.Error(error?.message?:error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override suspend fun setUserDetails(
        userid: String,
        userName: String,
        phone: String,
        deliveryAddress: String,
    ): Flow<Response<Boolean>> = flow {
        operationSuccessul = false
        try {
            val userObj = mutableMapOf<String, String>()
            userObj["userName"] = userName
            userObj["phone"] = phone
            userObj["deliveryAddress"] = deliveryAddress
            database.collection(Constants.COLLECTION_NAME_USERS).document(userid).update(userObj as Map<String, Any>).addOnSuccessListener { operationSuccessul = true }.await()

            if(operationSuccessul) {
                emit(Response.Success(operationSuccessul))
            }
            else {
                emit(Response.Error("Ошибка при обновлении данных пользователя"))
            }
        }
        catch (e: Exception) {
            Response.Error(e.localizedMessage?:"Непредвиденная ошибка")
        }
    }
}