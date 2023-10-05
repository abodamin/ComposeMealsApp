package com.abdullah.composeapp.data.repository

import com.abdullah.composeapp.data.network.MealsModel
import com.abdullah.composeapp.data.network.RestApi
import javax.inject.Inject


class MealsRepository @Inject constructor(
    private var retrofit: RestApi,
) {

    init {

    }

    suspend fun getMealsRepository(): MealsModel{
        return retrofit.getMealsList()
    }
}