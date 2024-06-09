package com.example.gd.data

import com.example.gd.domain.model.Point
import com.example.gd.domain.model.Product
import com.example.gd.domain.model.User
import com.example.gd.domain.repositories.PointRepository
import com.example.gd.util.Constants
import com.example.gd.util.Response
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(
    private val database: FirebaseFirestore
): PointRepository {
    private var operationSuccessul = false
    override suspend fun getPointList(): Flow<Response<List<Point>>> = callbackFlow {
        val snapShotListener = database.collection(Constants.COLLECTION_NAME_POINTS)
            .addSnapshotListener { snapshot, error ->
                val response = if (snapshot != null) {
                    val pointInfo = snapshot.toObjects(Point::class.java)
                    Response.Success<List<Point>>(pointInfo!!)
                } else {
                    Response.Error(error?.message ?: error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }


    override suspend fun setPoint(userid: String, point: Point): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        operationSuccessul = false
        try {
            val userObj = mutableMapOf<String, String>()
            userObj["selectedPoint"] = point.id
            database.collection(Constants.COLLECTION_NAME_USERS)
                .document(userid)
                .update(userObj as Map<String, Any>)
                .addOnSuccessListener { operationSuccessul = true }.await()

            if(operationSuccessul) {
                emit(Response.Success(operationSuccessul))
            }
            else {
                emit(Response.Error("Ошибка при обновлении данных точки"))
            }
        }
        catch (e: Exception) {
            emit(Response.Error(e.localizedMessage?:"Непредвиденная ошибка"))
        }
    }

    override suspend fun getPoint(userid: String): Flow<Response<Point>> = callbackFlow {
        val pointId = try {
            val userDoc = database.collection(Constants.COLLECTION_NAME_USERS).document(userid).get().await()
            userDoc.data?.get("selectedPoint") as? String ?: ""
        } catch (e: Exception) {
            //emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
            return@callbackFlow // Exit the coroutine if there's an error
        }

        var pointListener: ListenerRegistration? = null
        if (pointId.isNotEmpty()) {
            pointListener = database.collection(Constants.COLLECTION_NAME_POINTS)
                .document(pointId)
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null) {
                        val currentPoint = snapshot.toObject(Point::class.java)
                        Response.Success<Point>(currentPoint!!)
                    } else {
                        Response.Error(error?.message ?: error.toString())
                    }
                    trySend(response)
                }
        }
        awaitClose { pointListener?.remove() }
    }
}