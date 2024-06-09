package com.example.gd.data

import android.util.Log
import com.example.gd.domain.model.Category
import com.example.gd.domain.model.User
import com.example.gd.domain.repositories.CategoryRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): CategoryRepository {
    private var operationSuccessful = false
    override suspend fun getCategoryList(): Flow<Response<List<Category>>> = callbackFlow {

        val snapShotListener = database.collection(Constants.COLLECTION_NAME_CATEGORIES)
            .orderBy("name")
            .addSnapshotListener {snapshot, error ->
                val response = if(snapshot != null) {
                    val categories = snapshot.toObjects(Category::class.java)
                    Response.Success<List<Category>>(categories)
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

    override suspend fun addNewCategory(
        name: String,
        categoryPhotoUrl: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            val categoryId = database.collection(Constants.COLLECTION_NAME_CATEGORIES).document().id
            val categoryObj = Category(
                id = categoryId,
                name = name,
                image = categoryPhotoUrl
            )

            database.collection(Constants.COLLECTION_NAME_CATEGORIES)
                .document(categoryId)
                .set(categoryObj)
                .addOnSuccessListener { operationSuccessful = true }.await()

            if(operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            }
            else {
                emit(Response.Error("Ошибка при добавлении категории"))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }

    }

    override suspend fun deleteCategory(name: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        emit(Response.Loading)
        try {
            var selectedCategory = ""
            database.collection(Constants.COLLECTION_NAME_CATEGORIES)
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener {
                    selectedCategory = it.documents.first().id
                }.addOnFailureListener {
                    Log.d("ERROR", it.localizedMessage.toString())
                }.await()

            database.collection(Constants.COLLECTION_NAME_CATEGORIES)
                .document(selectedCategory)
                .delete().await()
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

}