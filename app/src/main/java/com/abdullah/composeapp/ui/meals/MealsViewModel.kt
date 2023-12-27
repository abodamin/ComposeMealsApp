package com.abdullah.composeapp.ui.meals

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abdullah.composeapp.data.network.MealsModel
import com.abdullah.composeapp.data.repository.MealsRepository
import com.abdullah.composeapp.ui.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state = mutableStateOf<List<MealsModel.Meal>>(listOf())
    var requestState = mutableStateOf<Resource<Any>?>(null)


    suspend fun getMeals(): Flow<Resource<Any>> {
        val get = savedStateHandle.get<String>("")
        return flow<Resource<Any>> {
            emit(Resource.Loading)

            mealsRepository.getMealsRepository().let {
                state.value = it.meals
                emit(Resource.Success())
            }

        }.catch { e ->
            emit(Resource.Error(e))
        }
    }
}