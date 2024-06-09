package com.example.gd.domain.use_cases.PointUseCases

import com.example.gd.domain.repositories.PointRepository
import javax.inject.Inject

class GetPointList @Inject constructor(
    private val repository: PointRepository
) {
    suspend operator fun invoke() = repository.getPointList()
}