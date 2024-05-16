package com.example.gd.data

import android.annotation.SuppressLint
import android.util.Log
import com.example.gd.domain.model.User
import com.example.gd.domain.repositories.AuthRepository
import com.example.gd.util.Constants
import com.example.gd.util.Password
import com.example.gd.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseFirestore
) : AuthRepository {
    private var operationSuccessful = false
    override fun isUserAuthenticatedInFirebase(): Boolean {
        return firebaseAuth.currentUser!=null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(firebaseAuth.currentUser == null)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun loginUser(email: String, password: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            operationSuccessful = result != null
            emit(Response.Success(operationSuccessful))
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    @SuppressLint("SimpleDateFormat")
    override suspend fun registerUser(
        email: String,
        password: String,
        userName: String,
        userRole: String
    ): Flow<Response<Boolean>> = flow {

        operationSuccessful = false
        emit(Response.Loading)
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            operationSuccessful = result != null

            Log.d("REGISTER", result.toString())
            if (operationSuccessful) {
                val userid = firebaseAuth.currentUser?.uid!!
                val registrationDate = SimpleDateFormat("dd.MM.yyyy").format(Date())
                val obj = User(
                    userid = userid,
                    userName = userName,
                    role = userRole.ifEmpty { Constants.ROLE_USER },
                    email = email,
                    password = Password.md5(password),
                    registrationDate = registrationDate
                )
                database.collection(Constants.COLLECTION_NAME_USERS).document(userid)
                    .set(obj).addOnSuccessListener {
                }.await()
                emit(Response.Success(operationSuccessful))
            }
            else {
                emit(Response.Success(operationSuccessful))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun signOutUser(): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            firebaseAuth.signOut()
            emit(Response.Success(true))
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun deleteUser(): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val userId = firebaseAuth.currentUser!!.uid
            firebaseAuth.currentUser!!.delete().addOnSuccessListener {
                operationSuccessful = true
                firebaseAuth.signOut()
                /*database.collection(Constants.COLLECTION_NAME_USERS)
                    .document(userId)
                    .delete()*/
            }.await()
            emit(Response.Success(operationSuccessful))
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }
}