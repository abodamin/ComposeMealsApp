package com.abdullah.composeapp.domain

import com.abdullah.composeapp.data.network.RestApi
import com.abdullah.composeapp.data.network.responses.MealsModel
import javax.inject.Inject

class GetMealByCategoryUseCase @Inject constructor(
    private val mealApi: RestApi
) {
    suspend operator fun  invoke(category: String): MealsModel{
        return mealApi.getMealsByCategory(category = category)
    }
}