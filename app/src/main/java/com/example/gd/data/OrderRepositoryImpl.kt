package com.example.gd.data

import android.net.nsd.NsdManager.RegistrationListener
import android.util.Log
import com.example.gd.R
import com.example.gd.domain.model.Order
import com.example.gd.domain.model.Product
import com.example.gd.domain.repositories.OrderRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): OrderRepository {
    private var operationSuccessful = false

    // ФУНКЦИЯ ПОЛУЧЕНИЯ СПИСКА ВСЕХ ЗАКАЗОВ ДЛЯ МЕНЕДЖЕРА
    override suspend fun getOrderList(): Flow<Response<List<Order>>> = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_ORDERS)
            .addSnapshotListener { snapshot, error ->
                // Если нет ошибки и есть данные, преобразуем их в список заказов
                val response = if (snapshot != null) {
                    val orders = snapshot.toObjects(Order::class.java)
                    Response.Success<List<Order>>(orders)
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

    // ФУНКЦИЯ ПОЛУЧЕНИЯ ИСТОРИИ ЗАКАЗОВ ДЛЯ ПОЛЬЗОВАТЕЛЯ
    override suspend fun getOrderListByUser(userid: String) = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_ORDERS)
            .whereEqualTo("userid", userid)
            .addSnapshotListener { snapshot, error ->
                // Если нет ошибки и есть данные, преобразуем их в список заказов
                val response = if (snapshot != null) {
                    val orders = snapshot.toObjects(Order::class.java)
                    Response.Success<List<Order>>(orders)
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

    // ФУНКЦИЯ ОФОРМЛЕНИЯ ЗАКАЗА
    override suspend fun arrangeOrder(
        userid: String,
        products: List<Product>,
        counts: List<Int>,
        orderType: String,
        deliveryAddress: String,
        pointAddress: String,
        totalPrice: Double
    ): Flow<Response<Boolean>> = flow {
        // Начальная установка флага успешной операции в false
        operationSuccessful = false
        // Эмитируем состояние загрузки
        emit(Response.Loading)
        try {
            // Генерация уникального идентификатора заказа
            val orderid = database.collection(Constants.COLLECTION_NAME_ORDERS).document().id
            val productnames = arrayListOf<String>()

            // Расчет баллов лояльности
            val loyaltyPoints = totalPrice / 100

            // Сбор имен продуктов из списка
            products.forEach {
                productnames.add(it.name)
            }
            // Создание объекта заказа
            val orderObj = Order(
                id = orderid,
                userid = userid,
                productNames = productnames.toList(),
                productCounts = counts,
                orderType = orderType,
                deliveryAddress = deliveryAddress,
                pointAddress = pointAddress,
                status = Constants.ORDER_CREATED,
                totalPrice = totalPrice
            )

            // Добавление заказа в коллекцию
            database.collection(Constants.COLLECTION_NAME_ORDERS)
                .document(orderid)
                .set(orderObj)
                .addOnSuccessListener { operationSuccessful = true }.await()

            // Обновление информации о пользователе
            val updates = hashMapOf<String, Any>(
                "loyaltyPoints" to loyaltyPoints,
                "orderList" to emptyList<String>()
            )

            database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .update(updates)
                .await()

            // Проверка успешности операции и эмитирование соответствующего результата
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при добавлении позиции меню"))
            }
        } catch (e: Exception) {
            // Обработка ошибок и эмитирование объекта ошибки
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    // ФУНКЦИЯ ПРИНЯТИЯ ЗАКАЗА МЕНЕДЖЕРОМ
    override suspend fun acceptOrder(orderid: String, status: String): Flow<Response<Boolean>> = flow {
        // Эмитируем состояние загрузки
        emit(Response.Loading)
        operationSuccessful = false
        try {
            // Создаем объект с обновлением статуса заказа
            val orderObj = mutableMapOf<String, String>()
            orderObj["status"] = status
            // Обновляем статус заказа в коллекции
            database.collection(Constants.COLLECTION_NAME_ORDERS)
                .document(orderid)
                .update(orderObj as Map<String, Any>)
                .addOnSuccessListener { operationSuccessful = true }.await()

            // Проверка успешности операции и эмитирование соответствующего результата
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при обновлении статуса заказа"))
            }
        } catch (e: Exception) {
            // Обработка ошибок и эмитирование объекта ошибки
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    // ФУНКЦИЯ ОТКЛОНЕНИЯ ЗАКАЗА МЕНЕДЖЕРОМ
    override suspend fun cancelOrder(orderid: String, status: String): Flow<Response<Boolean>> = flow {
        // Эмитируем состояние загрузки
        emit(Response.Loading)
        operationSuccessful = false
        try {
            // Создаем объект с обновлением статуса заказа
            val orderObj = mutableMapOf<String, String>()
            orderObj["status"] = status
            // Обновляем статус заказа в коллекции
            database.collection(Constants.COLLECTION_NAME_ORDERS)
                .document(orderid)
                .update(orderObj as Map<String, Any>)
                .addOnSuccessListener { operationSuccessful = true }.await()

            // Проверка успешности операции и эмитирование соответствующего результата
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при обновлении статуса заказа"))
            }
        } catch (e: Exception) {
            // Обработка ошибок и эмитирование объекта ошибки
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }


}