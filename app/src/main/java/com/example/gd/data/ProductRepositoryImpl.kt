package com.example.gd.data

import android.util.Log
import com.example.gd.R
import com.example.gd.domain.model.Product
import com.example.gd.domain.repositories.ProductRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.getField
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): ProductRepository {
    private var operationSuccessful = false
    override suspend fun getProductList(): Flow<Response<List<Product>>> = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_PRODUCTS)
            .addSnapshotListener {snapshot, error ->
                val response = if(snapshot != null) {
                    val products = snapshot.toObjects(Product::class.java)
                    Response.Success<List<Product>>(products)
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

    override suspend fun getProductsByCategory(categoryid: String): Flow<Response<List<Product>>> = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_PRODUCTS)
            .whereEqualTo("categoryId", categoryid)
            .addSnapshotListener {snapshot, error ->
                val response = if(snapshot != null) {
                    val products = snapshot.toObjects(Product::class.java)
                    Response.Success<List<Product>>(products)
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

    override suspend fun addProduct(
        image: String,
        name: String,
        weight: String,
        calories: String,
        price: Double,
        categoryName: String
    ): Flow<Response<Boolean>> = flow {
        try {
            val productId = database.collection(Constants.COLLECTION_NAME_PRODUCTS).document().id
            var categoryid = ""
            database.collection(Constants.COLLECTION_NAME_CATEGORIES)
                .whereEqualTo("name", categoryName)
                .get()
                .addOnSuccessListener {
                    categoryid = it.documents.first().id
                }.addOnFailureListener {
                    Log.d("ERROR", it.localizedMessage.toString())
                }.await()

            val productObj = Product(
                id = productId,
                image = image,
                name = name,
                weight = weight,
                calories = calories,
                price = price,
                categoryId = categoryid
            )

            database.collection(Constants.COLLECTION_NAME_PRODUCTS)
                .document(productId)
                .set(productObj)
                .addOnSuccessListener { operationSuccessful = true }.await()

            if(operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            }
            else {
                emit(Response.Error("Ошибка при добавлении позиции меню"))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun getFavoriteById(userid: String): Flow<Response<List<Product>>> = callbackFlow {
        val productsId = try {
            val userDoc = database.collection(Constants.COLLECTION_NAME_USERS).document(userid).get().await()
            userDoc.data?.get("favorites") as? List<String> ?: emptyList()  // Return empty list if orderList is null
        } catch (e: Exception) {
            //emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
            return@callbackFlow // Exit the coroutine if there's an error
        }

        var productListener: ListenerRegistration? = null
        if (productsId.isNotEmpty()) {
            val productList = mutableListOf<Product>()
            productListener = database.collection(Constants.COLLECTION_NAME_PRODUCTS)
                .whereIn(FieldPath.documentId(), productsId)
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null) {
                        val products = snapshot.toObjects(Product::class.java)
                        productList.clear() // Clear existing products before adding new ones
                        productList.addAll(products)
                        Response.Success(productList)
                    } else {
                        Response.Error(error?.message ?: error.toString())
                    }
                    trySend(response)
                }
        }
        awaitClose { productListener?.remove() }
    }

    override suspend fun getOrderById(userid: String): Flow<Response<List<Product>>> = callbackFlow {
        val productsId = try {
            val userDoc = database.collection(Constants.COLLECTION_NAME_USERS).document(userid).get().await()
            userDoc.data?.get("orderList") as? List<String> ?: emptyList()  // Return empty list if orderList is null
        } catch (e: Exception) {
            //emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
            return@callbackFlow // Exit the coroutine if there's an error
        }

        var productListener: ListenerRegistration? = null
        if (productsId.isNotEmpty()) {
            val productList = mutableListOf<Product>()
            productListener = database.collection(Constants.COLLECTION_NAME_PRODUCTS)
                .whereIn(FieldPath.documentId(), productsId)
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null) {
                        val products = snapshot.toObjects(Product::class.java)
                        productList.clear() // Clear existing products before adding new ones
                        productList.addAll(products)
                        Response.Success(productList)
                    } else {
                        Response.Error(error?.message ?: error.toString())
                    }
                    trySend(response)
                }
        }
        awaitClose { productListener?.remove() }
    }

    override suspend fun addProductInFavorite(
        productid: String,
        userid: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val favList = arrayListOf<String>()
            favList.add(productid)

            database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .get()
                .addOnSuccessListener {
                    database.collection(Constants.COLLECTION_NAME_USERS)
                        .document(userid)
                        .update("favorites", FieldValue.arrayUnion(productid))
                    operationSuccessful = true
                }.await()

            if(operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            }
            else {
                emit(Response.Error("Ошибка при добавлении в избранное"))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun addProductInOrder(productid: String, userid: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val orderList = arrayListOf<String>()
            orderList.add(productid)

            database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .get()
                .addOnSuccessListener {
                    database.collection(Constants.COLLECTION_NAME_USERS)
                        .document(userid)
                        .update("orderList", FieldValue.arrayUnion(productid))
                    operationSuccessful = true
                }.await()

            if(operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            }
            else {
                emit(Response.Error("Ошибка при добавлении в заказ"))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun deleteProduct(productName: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            var selectedProduct = ""
            database.collection(Constants.COLLECTION_NAME_PRODUCTS)
                .whereEqualTo("name", productName)
                .get()
                .addOnSuccessListener {
                    selectedProduct = it.documents.first().id
                }.addOnFailureListener {
                    Log.d("ERROR", it.localizedMessage.toString())
                }.await()

            database.collection(Constants.COLLECTION_NAME_PRODUCTS)
                .document(selectedProduct)
                .delete().await()
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun deleteFromOrder(
        productid: String,
        userid: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val userDoc = database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .get()
                .await()

            val orderList = userDoc.get("orderList") as? MutableList<String> ?: mutableListOf()

            if (orderList.remove(productid)) {
                database.collection(Constants.COLLECTION_NAME_USERS)
                    .document(userid)
                    .update("orderList", orderList)
                    .await()

                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при удалении товара"))
            }

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

    override suspend fun deleteFromFavorite(
        productid: String,
        userid: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val userDoc = database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .get()
                .await()

            val favList = userDoc.get("favorites") as? MutableList<String> ?: mutableListOf()

            if (favList.remove(productid)) {
                database.collection(Constants.COLLECTION_NAME_USERS)
                    .document(userid)
                    .update("favorites", favList)
                    .await()

                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Ошибка при удалении из избранного"))
            }

        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Непредвиденная ошибка"))
        }
    }

}