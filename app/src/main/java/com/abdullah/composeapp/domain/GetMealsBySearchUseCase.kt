package com.abdullah.composeapp.domain

import com.abdullah.composeapp.data.network.RestApi
import com.abdullah.composeapp.data.network.responses.SearchMealResponse
import javax.inject.Inject

class GetMealsBySearchUseCase @Inject constructor(
    private val restApi: RestApi,
) {
    suspend operator fun invoke(search: String): SearchMealResponse{
        return restApi.getMealsBySearch(search)
    }
}