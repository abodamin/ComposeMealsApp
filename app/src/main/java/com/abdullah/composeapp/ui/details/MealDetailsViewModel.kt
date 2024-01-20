package com.abdullah.composeapp.ui.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abdullah.composeapp.data.network.responses.MealDetailsResponse
import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.domain.AddToFavoriteUseCase
import com.abdullah.composeapp.domain.MealDetailsUseCase
import com.abdullah.composeapp.ui.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val mealDetailsUseCase: MealDetailsUseCase,
) : ViewModel() {

    var mealObject: MealsModel.Meal? = null
    private var isItemFavorite: MutableState<Boolean> = mutableStateOf(false)

    val requestState : MutableState<Resource<MealDetailsResponse>> = mutableStateOf(Resource.Loading)
    lateinit var data: MealDetailsResponse

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

    suspend fun getMealDetails(): Flow<Resource<MealDetailsResponse>> {
        return flow<Resource<MealDetailsResponse>>{
            emit(Resource.Loading)
            data = mealDetailsUseCase(mealObject!!.idMeal)
            emit(Resource.Success())
        }.catch {it ->
            emit(Resource.Error(it))
        }
    }
}