package com.abdullah.composeapp.ui.meals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.abdullah.composeapp.data.navigation.NavTarget
import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.ui.common.GeneralErrorScreen
import com.abdullah.composeapp.ui.models.Resource
import com.google.gson.Gson
import java.net.URLEncoder


@Preview(showBackground = true)
@Composable
fun MealsPagePreview() {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(10) { item ->
                    MealCard(
                        meals = MealsModel(
                            meals = listOf(
                                MealsModel.Meal(
                                    "",
                                    "Egyptian Fattah with Rice and Banana",
                                    "https://picsum.photos/seed/picsum/200/300",
                                ),
                                MealsModel.Meal(
                                    "",
                                    "Ma3soob",
                                    "https://picsum.photos/seed/picsum/200/300",
                                ),
                                MealsModel.Meal(
                                    "",
                                    "TITLE TITLE TITLE TITILE TITILE TITLE TITLE TILE ",
                                    "https://picsum.photos/seed/picsum/200/300",
                                ),

                                )
                        ).meals[item]
                    )
                }
            }
        }
    }
}

@Composable
fun MealsPage(
    navController: NavController,
    mViewModel: MealsViewModel,
) {

    //start on launching this Composable (like initState() in Flutter )
    LaunchedEffect(mViewModel) {
        mViewModel.getMeals().collect {
            mViewModel.requestState.value = it
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)

        ) {
            TopAppBar {
                Text(text = "Meals App", style = MaterialTheme.typography.subtitle1)
            }

            val selectedIndex by mViewModel.selectedCategory.collectAsState()

            LazyRow(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)){
                items(mViewModel.createSampleCategoryList().size){
                    FilterChipExample(
                        title = mViewModel.createSampleCategoryList()[it].strCategory,
                        onClick = {
                            mViewModel.setCategory(it)
                        },
                        selected = it == selectedIndex
                    )

                }
            }

            when (mViewModel.requestState.value) {
                is Resource.Loading -> {
                    LoadingView()
                }

                is Resource.Error -> {
                    GeneralErrorScreen()
                }

                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(mViewModel.state.value.toList().size) { item ->
                            MealCard(meals = mViewModel.state.value[item], navController)
                        }
                    }
                }

                null -> {}
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
    ) {
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
private fun MealCard(meals: MealsModel.Meal, navController: NavController? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(350.dp)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(0.dp)
                .clickable {
                    val mealJson = URLEncoder.encode(Gson().toJson(meals), "UTF-8")
                    navController?.navigate("${NavTarget.MealDetailsPage.name}/${mealJson}")
                }
        ) {
            val (ivFood, tvTitle, tvDescription, shadow) = createRefs()
            val centerGuideline = createGuidelineFromTop(0.45f)
            createVerticalChain(tvTitle, tvDescription, chainStyle = ChainStyle.Packed(0.9f))

            Image(
                painter = rememberAsyncImagePainter(meals.strMealThumb),
                contentDescription = "",
                modifier = Modifier
                    .padding(0.dp)
                    .constrainAs(ivFood) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                contentScale = ContentScale.FillBounds,
            )

//            shadow effect
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 1100f
                        )
                    )
                    .graphicsLayer {
                        // Apply the blur effect based on the 'blurEnabled' state
                        alpha = if (true) 0.7f else 1f
                    }
                    .constrainAs(shadow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                meals.strMeal,
                Modifier
                    .padding(all = 8.dp)
                    .constrainAs(tvTitle) {
                        top.linkTo(centerGuideline)
                        bottom.linkTo(tvDescription.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                        verticalChainWeight = 1f
                    },
                style = TextStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.typography.h5.fontSize
                )
            )
            Text(
                "Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients Ingredients ",
                Modifier
                    .padding(all = 8.dp)
                    .constrainAs(tvDescription) {
                        top.linkTo(tvTitle.bottom)
                        start.linkTo(tvTitle.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.wrapContent
                        verticalChainWeight = 1f
                    },
                style = TextStyle(
                    color = Gray,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }

}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChipExample(title: String, selected: Boolean = false, onClick: ()->Unit) {
    FilterChip(
        modifier = Modifier.padding(all = 4.dp),
        selected = selected,
        onClick = onClick,
        content = {
            Text(title)
        },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected icon",
                    modifier = Modifier.size(18.dp)
                )
            }
        } else {
            null
        },
    )
}