package com.abdullah.composeapp.ui.meals

import android.widget.ImageView.ScaleType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import coil.compose.rememberAsyncImagePainter

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdullah.composeapp.data.network.MealsModel
import com.abdullah.composeapp.ui.theme.Grey

@Preview(showBackground = true)
@Composable
fun MealsPagePreview() {
    MealsPage()
}

@Composable
fun MealsPage() {
    val viewModel: MealsViewModel = MealsViewModel()

    LaunchedEffect(key1 = "123", block = {
        viewModel.getMeals()
    })

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            TopAppBar {
                Text(text = "Meals App", style = MaterialTheme.typography.subtitle1)
            }
//            Card
            MealCard(viewModel.state.value)
        }
    }
}


@Composable
private fun MealCard(meals: List<MealsModel.Meal>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 8.dp)){
            Image(
                painter = rememberAsyncImagePainter(if(meals.isNotEmpty()) meals[0].strMealThumb else "" ),
                contentDescription = "",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillBounds,

            )
            Column {
                Text(if(meals.isEmpty()) "Meal Name" else meals.get(0).strMeal, Modifier.padding(all = 8.dp))
                Text(
                    "Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients ",
                    Modifier.padding(all = 8.dp),
                    style = androidx.compose.ui.text.TextStyle(color = Grey)
                )
            }
        }
    }
}