package com.example.gd.domain.use_cases.PointUseCases

import com.example.gd.domain.model.Point
import com.example.gd.domain.repositories.PointRepository
import javax.inject.Inject

class SetPoint @Inject constructor(
    private val repository: PointRepository
) {
    suspend operator fun invoke(userid: String, point: Point) = repository.setPoint(userid, point)
}