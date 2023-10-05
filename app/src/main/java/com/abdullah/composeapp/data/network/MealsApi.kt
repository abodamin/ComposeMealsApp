package com.abdullah.composeapp.data.network

import retrofit2.http.GET

interface RestApi {

    @GET("1/filter.php?a=Egyptian")
    suspend fun getMealsList():MealsModel

}