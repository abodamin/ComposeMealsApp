package com.abdullah.composeapp.ui.meals

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.data.repository.MealsRepository
import com.abdullah.composeapp.domain.GetMealByCategoryUseCase
import com.abdullah.composeapp.domain.GetMealsBySearchUseCase
import com.abdullah.composeapp.domain.models.Category
import com.abdullah.composeapp.domain.models.CategoryConstants
import com.abdullah.composeapp.ui.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealsRepository: MealsRepository,
    private val getMealByCategoryUseCase: GetMealByCategoryUseCase,
    private val getMealsBySearchUseCae: GetMealsBySearchUseCase,
) : ViewModel() {

    val selectedCategory = MutableStateFlow<Int>(0)
    var state = mutableStateOf<List<MealsModel.Meal>>(listOf())
    var requestState = mutableStateOf<Resource<Any>?>(null)


    suspend fun getMeals(): Flow<Resource<Any>> {
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

    fun createSampleCategoryList(): MutableList<Category> {
        return CategoryConstants.categories.toMutableList()
    }

    fun setCategory(newIndex: Int) {
        selectedCategory.value = newIndex
    }

    fun getMealByCategory(category: String ="Beef"): Flow<Resource<Any>> {
        return flow<Resource<Any>> {
            emit(Resource.Loading)
            getMealByCategoryUseCase(category).let {
                state.value = it.meals
                emit(Resource.Success())
            }
            emit(Resource.Success())
        }.catch { e ->
            emit(Resource.Error(e))
        }
    }

    suspend fun getMealsBySearch(search: String):  Flow<Resource<Any>> {
        return flow<Resource<Any>> {
            emit(Resource.Loading)
            getMealsBySearchUseCae(search).let { it1 ->
                state.value = (it1.meals).map { MealsModel.Meal(it.idMeal!!, it.strMeal!!, it.strMealThumb!!) }
                emit(Resource.Success())
            }
        }.catch { e ->
            Timber.e(e.stackTraceToString())
            emit(Resource.Error(e))
        }
    }

}