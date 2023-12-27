package com.abdullah.composeapp.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable()
fun MealDetailsPage(viewModel: MealDetailsViewModel, mealId: String) {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize()){
            Text(mealId,  modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MealDetailsPreview(){
    Scaffold {
        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()){
            Text("15", fontSize = 38.sp, modifier = Modifier.align(Alignment.Center))
        }
    }
}