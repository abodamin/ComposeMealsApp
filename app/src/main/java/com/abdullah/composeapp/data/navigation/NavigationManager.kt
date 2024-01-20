package com.abdullah.composeapp.data.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.ui.common.CommonKeys.Companion.MEAL_OBJECT
import com.abdullah.composeapp.ui.details.MealDetailsPage
import com.abdullah.composeapp.ui.details.MealDetailsViewModel
import com.abdullah.composeapp.ui.meals.MealsPage
import com.abdullah.composeapp.ui.meals.MealsViewModel
import com.google.gson.Gson
import java.net.URLDecoder

@Composable
fun NavigationComponent(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavTarget.MealsPage.name
    ) {

        composable(NavTarget.MealsPage.name) {
            val hiltViewModel = hiltViewModel<MealsViewModel>()
            MealsPage(navController, hiltViewModel)
        }

        composable(
            route = "${NavTarget.MealDetailsPage.name}/{$MEAL_OBJECT}",
            arguments = listOf(navArgument(MEAL_OBJECT) { type = NavType.StringType })
        ) { backStackEntry ->
            val mealJson = backStackEntry.arguments?.getString(MEAL_OBJECT)!!
            val mealObject = Gson().fromJson(URLDecoder.decode(mealJson, "UTF-8"), MealsModel.Meal::class.java)

            val viewModel: MealDetailsViewModel = hiltViewModel<MealDetailsViewModel>()

            MealDetailsPage(viewModel, navController ,mealObject)
        }
    }

}