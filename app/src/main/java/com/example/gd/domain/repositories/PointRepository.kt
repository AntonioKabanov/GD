package com.example.gd.domain.repositories

import com.example.gd.domain.model.Order
import com.example.gd.domain.model.Point
import com.example.gd.util.Response
import kotlinx.coroutines.flow.Flow

interface PointRepository {
    suspend fun getPointList(): Flow<Response<List<Point>>>
    suspend fun setPoint(userid: String, point: Point): Flow<Response<Boolean>>
    suspend fun getPoint(userid: String): Flow<Response<Point>>
}