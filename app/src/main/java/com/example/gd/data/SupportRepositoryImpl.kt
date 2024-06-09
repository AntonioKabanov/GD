package com.example.gd.data

import android.util.Log
import com.example.gd.R
import com.example.gd.domain.model.Product
import com.example.gd.domain.model.SupportMessage
import com.example.gd.domain.repositories.SupportRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SupportRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): SupportRepository {
    private var operationSuccessful = false
    // ПОЛУЧЕНИЕ ВСЕХ СООБЩЕНИЙ ОТ ПОЛЬЗОВАТЕЛЕЙ ДЛЯ АДМИНИСТРАТОРА
    override suspend fun getAllMessages(): Flow<Response<List<SupportMessage>>> = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_SUPPORT)
            .addSnapshotListener { snapshot, error ->
                // Если данные успешно получены, преобразуем их в список сообщений
                val response = if (snapshot != null) {
                    val messages = snapshot.toObjects(SupportMessage::class.java)
                    Response.Success<List<SupportMessage>>(messages)
                } else {
                    // Иначе создаем объект ошибки с соответствующим сообщением
                    Response.Error(error?.message ?: error.toString())
                }
                // Отправляем результат через callbackFlow
                trySend(response).isSuccess
            }
        // Завершаем работу слушателя при закрытии callbackFlow
        awaitClose {
            snapShotListener.remove()
        }
    }

    // ПОЛУЧЕНИЕ ВСЕХ СООБЩЕНИЙ ТЕХ. ПОДДЕРЖКИ ОБЫЧНЫМ ПОЛЬЗОВАТЕЛЕМ
    override suspend fun getUserMessages(userid: String): Flow<Response<List<SupportMessage>>> = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_SUPPORT)
            .whereEqualTo("userid", userid)
            .addSnapshotListener { snapshot, error ->
                // Если данные успешно получены, преобразуем их в список сообщений
                val response = if (snapshot != null) {
                    val messages = snapshot.toObjects(SupportMessage::class.java)
                    Response.Success<List<SupportMessage>>(messages)
                } else {
                    // Иначе создаем объект ошибки с соответствующим сообщением
                    Response.Error(error?.message ?: error.toString())
                }
                // Отправляем результат через callbackFlow
                trySend(response).isSuccess
            }
        // Завершаем работу слушателя при закрытии callbackFlow
        awaitClose {
            snapShotListener.remove()
        }
    }

    // ОТПРАВКА СООБЩЕНИЯ ПОЛЬЗОВАТЕЛЕМ В ТЕХ. ПОДДЕРЖКУ
    override suspend fun sendMessage(
        userid: String,
        header: String,
        question: String
    ): Flow<Response<Boolean>> = flow {
        // Начальная установка флага успешной операции в false
        operationSuccessful = false
        try {
            // Генерация уникального идентификатора сообщения
            val messageId = database.collection(Constants.COLLECTION_NAME_SUPPORT).document().id

            // Создание объекта сообщения
            val messageObj = SupportMessage(
                id = messageId,
                userid = userid,
                header = header,
                question = question
            )

            // Добавление сообщения в коллекцию
            database.collection(Constants.COLLECTION_NAME_SUPPORT)
                .document(messageId)
                .set(messageObj)
                .addOnSuccessListener { operationSuccessful = true }.await()

            // Проверка успешности операции и эмитирование соответствующего результата
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при отправке сообщения"))
            }
        } catch (e: Exception) {
            // Обработка ошибок и эмитирование объекта ошибки
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    // ОТПРАВКА ОТВЕТА АДМИНИСТРАТОРА НА СООБЩЕНИЕ ПОЛЬЗОВАТЕЛЯ В ТЕХ. ПОДДЕРЖКЕ
    override suspend fun sendAnswer(messageid: String, answer: String): Flow<Response<Boolean>> = flow {
        // Эмитируем состояние загрузки
        emit(Response.Loading)
        operationSuccessful = false
        try {
            // Создаем объект с ответом на сообщение
            val messageObj = mutableMapOf<String, String>()
            messageObj["answer"] = answer
            // Обновляем сообщение в коллекции
            database.collection(Constants.COLLECTION_NAME_SUPPORT)
                .document(messageid)
                .update(messageObj as Map<String, Any>)
                .addOnSuccessListener { operationSuccessful = true }.await()

            // Проверка успешности операции и эмитирование соответствующего результата
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при отправке ответа"))
            }
        } catch (e: Exception) {
            // Обработка ошибок и эмитирование объекта ошибки
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }
}