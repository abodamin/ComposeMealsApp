package com.abdullah.composeapp.data.repository

import com.abdullah.composeapp.data.network.MealsApi
import com.abdullah.composeapp.data.network.MealsModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealsRepository() {

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    suspend fun getMealsRepository(): MealsModel{
        val api: MealsApi = retrofit.create(MealsApi::class.java)
        return api.getMealsList()
    }
}