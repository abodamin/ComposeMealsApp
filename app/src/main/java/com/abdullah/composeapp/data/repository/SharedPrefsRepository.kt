package com.abdullah.composeapp.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun setFavoriteMeal(mealId :String ,data: String) {
        sharedPreferences.edit().putString(mealId, data).apply()
    }

    fun getFavoriteMeal(mealId: String): String? {
        return sharedPreferences.getString(mealId, null)
    }

    fun clearFavoriteMeal(mealId: String){
        return sharedPreferences.edit().remove(mealId).apply()
    }


}