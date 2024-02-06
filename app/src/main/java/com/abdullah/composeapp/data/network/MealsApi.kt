package com.abdullah.composeapp.data.network

import com.abdullah.composeapp.data.network.responses.MealDetailsResponse
import com.abdullah.composeapp.data.network.responses.MealsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("1/filter.php?a=Egyptian")
    suspend fun getMealsList(): MealsModel
    @GET("1/lookup.php")
    suspend fun mealLookUp(@Query("i") id: String): MealDetailsResponse

    @GET("1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsModel
}