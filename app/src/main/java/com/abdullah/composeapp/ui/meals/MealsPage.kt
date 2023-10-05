package com.abdullah.composeapp.ui.meals


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.abdullah.composeapp.data.network.MealsModel
import com.abdullah.composeapp.ui.models.Resource
import com.abdullah.composeapp.ui.theme.Grey

@Preview(showBackground = true)
@Composable
fun MealsPagePreview() {
    MealsPage()
}

@Composable
fun MealsPage(
    viewModel: MealsViewModel = viewModel()
) {

    LaunchedEffect(viewModel){
        viewModel.getMeals().collect{
            viewModel.requestState.value = it
        }
    }

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

            when (viewModel.requestState.value) {
                is Resource.Loading -> {
                    LoadingView()
                }
                is Resource.Error -> {
                    GeneralErrorScreen()
                }
                is Resource.Success ->{

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(viewModel.state.value.toList().size){ item ->
                            MealCard(meals = viewModel.state.value[item])
                        }
                    }
                }
                null ->{}
                else -> {}
            }

        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )  {
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = true,
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun MealCard(meals: MealsModel.Meal) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            Row {

                Image(
                    painter = rememberAsyncImagePainter(meals.strMealThumb),
                    contentDescription = "",
                    modifier = Modifier.size(90.dp),
                    contentScale = ContentScale.FillBounds,

                    )
                Column {
                    Text(meals.strMeal, Modifier.padding(all = 8.dp))
                    Text(
                        "Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients ",
                        Modifier.padding(all = 8.dp),
                        style = TextStyle(color = Grey)
                    )
                }
            }
        }

}


@Composable
fun GeneralErrorScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {

    Text(text = "Ops! something wrong happened",)
    }
}