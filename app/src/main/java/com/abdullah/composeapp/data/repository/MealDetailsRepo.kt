package com.abdullah.composeapp.data.repository

import com.abdullah.composeapp.data.network.RestApi
import com.abdullah.composeapp.data.network.responses.MealDetailsResponse
import javax.inject.Inject

class MealDetailsRepo @Inject constructor(
    private val restApi: RestApi
) {
    suspend operator fun invoke(id: String): MealDetailsResponse {
        return restApi.mealLookUp(id)
    }
}