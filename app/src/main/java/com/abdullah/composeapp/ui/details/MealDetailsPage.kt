package com.abdullah.composeapp.ui.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.abdullah.composeapp.R
import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.ui.common.GeneralErrorScreen
import com.abdullah.composeapp.ui.models.Resource
import com.abdullah.composeapp.ui.theme.Shapes
import com.abdullah.composeapp.ui.theme.appSurfaceColor
import com.abdullah.composeapp.ui.theme.primaryColor
import com.abdullah.composeapp.ui.theme.secondaryColorDark
import timber.log.Timber


@Composable()
fun MealDetailsPage(
    viewModel: MealDetailsViewModel = hiltViewModel<MealDetailsViewModel>(),
    navController: NavController?,
    meal: MealsModel.Meal,
    isPreview: Boolean? = false
) {
    if (viewModel == null && !isPreview!!) return
    val isFavorite by remember { derivedStateOf { viewModel?.isFavorite() ?: false } }
    val image = painterResource(id = R.drawable.ic_launcher_foreground)




    //setUp ViewModel
    viewModel?.mealObject = meal
    viewModel?.isFavorite()

    // Trigger the Bluetooth scanning when the composable is first launched
    LaunchedEffect(viewModel) {
        viewModel.getMealDetails().collect{
            viewModel.requestState.value = it
        }
    }

    Scaffold {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(color = appSurfaceColor)
                .verticalScroll(rememberScrollState())
                .padding(it.calculateBottomPadding())
        ) {
            val (appBar, ivMeal, title, about, description, loader, ingredientsTitle, ingredientsValue) = createRefs()
            val startGuideline = createGuidelineFromStart(16.dp)
            val endGuideline = createGuidelineFromEnd(16.dp)

            TopAppBar(
                title = { Text("") },
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "go back",
                        tint = primaryColor,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                navController!!.popBackStack()
                            }
                    )
                },
                modifier = Modifier.constrainAs(appBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                })

            //Image
            Card(
                elevation = 10.dp,
                modifier = Modifier
                    .aspectRatio(1.15f)
                    .padding(vertical = 16.dp)
                    .constrainAs(ivMeal) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(appBar.bottom)
                        width = Dimension.fillToConstraints
                    },
                shape = Shapes.medium,
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = if (isPreview == true) image else rememberAsyncImagePainter(meal.strMealThumb),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxSize()
                            .background(Color.Gray),
                        contentScale = ContentScale.FillBounds,
                    )
                    Icon(
                        if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .clickable {
                                viewModel?.toggleFavoriteMeal()
                            },
                        tint = Color.Red.copy(0.6f),
                        contentDescription = null,
                    )
                }
            }


//                ------
            Text(meal.strMeal,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        start.linkTo(startGuideline)
                        end.linkTo(parent.end)
                        top.linkTo(ivMeal.bottom)
                        width = Dimension.fillToConstraints
                    })
            //                ------
            when (viewModel?.requestState!!.value) {
                is Resource.Loading -> {
                    Log.d("","___Resource.Loading")
                    CircularProgressIndicator(
                        modifier = Modifier
                            .constrainAs(loader) {
                                start.linkTo(startGuideline)
                                end.linkTo(endGuideline)
                                top.linkTo(title.bottom)
                                bottom.linkTo(parent.bottom)
                                visibility = if (viewModel.requestState.value is Resource.Loading) Visibility.Visible else Visibility.Gone
                            }
                    )
                }

                is Resource.Error -> {
                    Timber.d("___Resource.Error")

                    GeneralErrorScreen(visibility = if (viewModel.requestState.value is Resource.Error) Visibility.Visible else Visibility.Gone)

                }

                is Resource.Success -> {
                    val data by remember{ derivedStateOf { viewModel.data }}

//                ---Ingredients
                    Text("Ingredients",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = secondaryColorDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 8.dp)
                            .constrainAs(ingredientsTitle) {
                                start.linkTo(startGuideline)
                                top.linkTo(title.bottom)
                                width = Dimension.wrapContent

                            })

                    Text(  data.meals.first().getIngredients().trim(),
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.constrainAs(ingredientsValue) {
                            start.linkTo(startGuideline)
                            end.linkTo(endGuideline)
                            top.linkTo(ingredientsTitle.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent

                        })

                    //                ------
                    Text("About Meal",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = secondaryColorDark,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(top = 24.dp, bottom = 8.dp)
                            .constrainAs(about) {
                                start.linkTo(startGuideline)
                                top.linkTo(ingredientsValue.bottom)
                                width = Dimension.wrapContent

                            })
                    Text(  data.meals.first().strInstructions?:"",
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(bottom = 40.dp).constrainAs(description) {
                            start.linkTo(startGuideline)
                            end.linkTo(endGuideline)
                            top.linkTo(about.bottom)
                            width = Dimension.fillToConstraints

                        })


                }
            }

        }
        //                ------
    }
}


@Preview(showBackground = true)
@Composable
fun MealDetailsPreview() {
    MealDetailsPage(
        isPreview = true,
        navController = null,
        meal = MealsModel.Meal(
            "1",
            "Spaghetti Bolognese",
            "https://picsum.photos/id/1/200/300"
        ),
    )
}