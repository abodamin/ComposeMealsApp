package com.abdullah.composeapp.data.network


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealsModel(
    @SerializedName("meals")
    val meals: List<Meal>
) : Parcelable {
    @Parcelize
    data class Meal(
        @SerializedName("idMeal")
        val idMeal: String,
        @SerializedName("strMeal")
        val strMeal: String,
        @SerializedName("strMealThumb")
        val strMealThumb: String
    ) : Parcelable
}