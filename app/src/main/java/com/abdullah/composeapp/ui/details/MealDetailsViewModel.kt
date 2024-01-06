package com.abdullah.composeapp.ui.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abdullah.composeapp.data.network.MealsModel
import com.abdullah.composeapp.domain.AddToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
) : ViewModel() {

    var mealObject: MealsModel.Meal? = null
    private var isItemFavorite: MutableState<Boolean> = mutableStateOf(false)

    fun toggleFavoriteMeal() {
        isItemFavorite.value = !isItemFavorite.value

        if (isItemFavorite.value) {
            addToFavoriteUseCase.setFavoriteMeal(mealObject!!.idMeal, mealObject!!)
        } else {
            addToFavoriteUseCase.clearFavoriteMeal(mealId = mealObject!!.idMeal)
        }
    }

    fun isFavorite(): Boolean {
         isItemFavorite.value = addToFavoriteUseCase.getFavoriteMeal(mealObject!!.idMeal) != null
        return isItemFavorite.value
    }


}