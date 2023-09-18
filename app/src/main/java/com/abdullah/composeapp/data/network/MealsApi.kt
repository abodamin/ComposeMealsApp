package com.abdullah.composeapp.data.network

import retrofit2.http.GET

interface MealsApi {

    @GET("filter.php?a=Egyptian")
    suspend fun getMealsList():MealsModel

}