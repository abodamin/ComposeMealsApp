package com.abdullah.composeapp.data.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdullah.composeapp.ui.details.MealDetailsPage
import com.abdullah.composeapp.ui.details.MealDetailsViewModel
import com.abdullah.composeapp.ui.meals.MealsPage
import com.abdullah.composeapp.ui.meals.MealsViewModel

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
            "${NavTarget.MealDetailsPage.name}/{mealId}",
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")!!
            val viewModel: MealDetailsViewModel = hiltViewModel<MealDetailsViewModel>()

            MealDetailsPage(viewModel, mealId)
        }
    }
}