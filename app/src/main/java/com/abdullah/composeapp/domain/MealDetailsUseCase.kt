package com.abdullah.composeapp.domain

import com.abdullah.composeapp.data.network.responses.MealDetailsResponse
import com.abdullah.composeapp.data.repository.MealDetailsRepo
import javax.inject.Inject

class MealDetailsUseCase @Inject constructor(
    private val mealDetailsRepo : MealDetailsRepo,
) {
    suspend operator fun invoke(id: String): MealDetailsResponse = mealDetailsRepo(id)
}