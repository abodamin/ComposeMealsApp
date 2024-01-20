package com.abdullah.composeapp.domain

import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.data.repository.SharedPrefsRepository
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository,

) {

    fun setFavoriteMeal(mealId :String ,data: MealsModel.Meal) {
        val mData = URLEncoder.encode(Gson().toJson(data), "UTF-8")
        return sharedPrefsRepository.setFavoriteMeal(mealId, mData);
    }

    fun getFavoriteMeal(mealId: String): MealsModel.Meal? {
        sharedPrefsRepository.getFavoriteMeal(mealId)?.apply {
            val decodedData = URLDecoder.decode(this, "UTF-8")
            return Gson().fromJson(decodedData, MealsModel.Meal::class.java)
        }
        return null
    }

    fun clearFavoriteMeal(mealId: String){
        return sharedPrefsRepository.clearFavoriteMeal(mealId)
    }

}