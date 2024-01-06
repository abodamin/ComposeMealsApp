package com.abdullah.composeapp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.abdullah.composeapp.data.network.MealsModel

@Composable()
fun MealDetailsPage(viewModel: MealDetailsViewModel?, mealObject: MealsModel.Meal) {
    Scaffold {
        Column {
            MealImageView(mealObject)
            Text("Title")
            Text("Rate")
            Text("Description")
            Text("Button")
        }
    }
}

@Composable
fun MealImageView(meal: MealsModel.Meal ) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    ) {
        Image(
            painter = rememberAsyncImagePainter(meal.strMealThumb),
            contentDescription = "",
            modifier = Modifier.padding(0.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MealDetailsPreview() {
    MealDetailsPage(viewModel = null, mealObject = MealsModel.Meal("1", "Spaghetti Bolognese", "https://picsum.photos/id/1/200/300"),)
}