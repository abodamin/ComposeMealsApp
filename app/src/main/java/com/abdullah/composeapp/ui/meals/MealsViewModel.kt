package com.abdullah.composeapp.ui.meals

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullah.composeapp.data.network.MealsModel
import com.abdullah.composeapp.data.repository.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MealsViewModel constructor(
    private val mealsRepository: MealsRepository = MealsRepository()
) : ViewModel() {

    var state = mutableStateOf<List<MealsModel.Meal>>(listOf())

    suspend fun getMeals() {
        viewModelScope.launch {
            mealsRepository.getMealsRepository().let {
                state.value = it.meals
            }
        }
    }
}