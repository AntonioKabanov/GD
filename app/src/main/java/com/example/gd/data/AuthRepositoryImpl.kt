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

    // ПРОВЕРКА СТАТУСА АУТЕНТИФИКАЦИИ ПОЛЬЗОВАТЕЛЯ В СИСТЕМЕ
    override fun isUserAuthenticatedInFirebase(): Boolean {
        // Возвращает true, если текущий пользователь авторизован в Firebase
        return firebaseAuth.currentUser != null
    }

    // ПОЛУЧЕНИЕ СОСТОЯНИЯ АУТЕНТИФИКАЦИИ ПОЛЬЗОВАТЕЛЯ
    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        // Создаем слушатель состояния аутентификации
        val authStateListener = FirebaseAuth.AuthStateListener {
            // Отправляем текущее состояние аутентификации: true, если пользователь не авторизован
            trySend(firebaseAuth.currentUser == null)
        }
        // Добавляем слушатель к FirebaseAuth
        firebaseAuth.addAuthStateListener(authStateListener)
        // Удаляем слушатель при закрытии callbackFlow
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    // ФУНКЦИЯ, РЕАЛИЗУЮЩАЯ ВХОД ПОЛЬЗОВАТЕЛЯ В СИСТЕМУ
    override suspend fun loginUser(email: String, password: String): Flow<Response<Boolean>> = flow {
        // Устанавливаем флаг успешной операции в false и эмитируем состояние загрузки
        operationSuccessful = false
        emit(Response.Loading)
        try {
            // Выполняем вход в систему с помощью email и пароля
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            // Устанавливаем флаг успешной операции, если результат не null
            operationSuccessful = result != null
            emit(Response.Success(operationSuccessful))
        } catch (e: Exception) {
            // Эмитируем ошибку с сообщением об ошибке
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    // ФУНКЦИЯ, РЕАЛИЗУЮЩАЯ РЕГИСТРАЦИЮ ПОЛЬЗОВАТЕЛЯ В СИСТЕМЕ
    @SuppressLint("SimpleDateFormat")
    override suspend fun registerUser(
        email: String,
        password: String,
        userName: String,
        userRole: String
    ): Flow<Response<Boolean>> = flow {
        // Устанавливаем флаг успешной операции в false и эмитируем состояние загрузки
        operationSuccessful = false
        emit(Response.Loading)
        try {
            // Регистрируем пользователя с помощью email и пароля
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            operationSuccessful = result != null

            Log.d("REGISTER", result.toString())
            if (operationSuccessful) {
                // Если регистрация успешна, создаем объект пользователя и сохраняем его в Firestore
                val userid = firebaseAuth.currentUser?.uid!!
                val registrationDate = SimpleDateFormat("dd.MM.yyyy").format(Date())
                val obj = User(
                    userid = userid,
                    userName = userName,
                    role = userRole.ifEmpty { Constants.ROLE_USER },
                    email = email,
                    password = Password.md5(password), // Хешируем пароль перед сохранением
                    registrationDate = registrationDate,
                    selectedPoint = ""
                )
                database.collection(Constants.COLLECTION_NAME_USERS).document(userid)
                    .set(obj).addOnSuccessListener {
                    }.await()
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Success(operationSuccessful))
            }
        } catch (e: Exception) {
            // Эмитируем ошибку с сообщением об ошибке
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    // ФУНКЦИЯ, РЕАЛИЗУЮЩАЯ ВЫХОД ПОЛЬЗОВАТЕЛЯ ИЗ СИСТЕМЫ
    override suspend fun signOutUser(): Flow<Response<Boolean>> = flow {
        // Эмитируем состояние загрузки
        emit(Response.Loading)
        try {
            // Выполняем выход из системы
            firebaseAuth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            // Эмитируем ошибку с сообщением об ошибке
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    // ФУНКЦИЯ, РЕАЛИЗУЮЩАЯ УДАЛЕНИЕ АККАУНТА ПОЛЬЗОВАТЕЛЯ ИЗ СИСТЕМЫ
    override suspend fun deleteUser(): Flow<Response<Boolean>> = flow {
        // Устанавливаем флаг успешной операции в false и эмитируем состояние загрузки
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val userId = firebaseAuth.currentUser!!.uid
            // Удаляем текущего пользователя из Firebase Authentication
            firebaseAuth.currentUser!!.delete().addOnSuccessListener {
                operationSuccessful = true
                firebaseAuth.signOut()
                // Закомментированный код для удаления пользователя из Firestore
                /*
                database.collection(Constants.COLLECTION_NAME_USERS)
                    .document(userId)
                    .delete()
                */
            }.await()
            emit(Response.Success(operationSuccessful))
        } catch (e: Exception) {
            // Эмитируем ошибку с сообщением об ошибке
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }
}