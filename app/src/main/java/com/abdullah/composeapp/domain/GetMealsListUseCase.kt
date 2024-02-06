package com.abdullah.composeapp.domain

import com.abdullah.composeapp.data.network.responses.MealDetailsResponse
import com.abdullah.composeapp.data.repository.MealDetailsRepo
import javax.inject.Inject

class GetMealsListUseCase @Inject constructor(
    private val mealsDetailsRepo: MealDetailsRepo
) {

    suspend operator fun invoke(id: String): MealDetailsResponse {
        return mealsDetailsRepo(id)
    }
}