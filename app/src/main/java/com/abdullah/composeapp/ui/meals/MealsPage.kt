@file:OptIn(ExperimentalComposeUiApi::class)

package com.abdullah.composeapp.ui.meals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ChipDefaults
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.abdullah.composeapp.data.navigation.NavTarget
import com.abdullah.composeapp.data.network.responses.MealsModel
import com.abdullah.composeapp.ui.common.GeneralErrorScreen
import com.abdullah.composeapp.ui.models.Resource
import com.abdullah.composeapp.ui.theme.boxGrey
import com.abdullah.composeapp.ui.theme.primaryColor
import com.abdullah.composeapp.ui.theme.primaryColorLight
import com.abdullah.composeapp.ui.theme.secondaryColorDark
import com.abdullah.composeapp.ui.theme.secondaryColorLight
import com.google.gson.Gson
import kotlinx.coroutines.launch
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MealsPage(
    navController: NavController,
    mViewModel: MealsViewModel,
) {
    val selectedIndex by mViewModel.selectedCategory.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var isSearching by remember { mutableStateOf(false) }
    var animate by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val collapsed = 18
    val expanded = 34
    val topAppBarTextSize = (collapsed + (expanded - collapsed) * (1 - scrollBehavior.state.collapsedFraction)).sp
    val topAppBarElementColor = if (scrollBehavior.state.collapsedFraction > 0.5) {
        primaryColor
    } else {
        Color.White
    }

    // Start on launching this Composable (like initState() in Flutter)
    LaunchedEffect(mViewModel) {
        animate = true
        mViewModel.getMealByCategory(mViewModel.createSampleCategoryList()[selectedIndex].strCategory)
            .collect {
                mViewModel.requestState.value = it
            }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Meals App", fontSize = topAppBarTextSize)
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = topAppBarElementColor,
                    titleContentColor = topAppBarElementColor,
                    actionIconContentColor = topAppBarElementColor,
                ),
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {

                item {
                    // Search text field
                    AnimatedVisibility(
                        visible = animate,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { -it }),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = searchText,
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .requiredHeight(androidx.compose.material3.TextFieldDefaults.MinHeight),
                                colors = androidx.compose.material3.TextFieldDefaults.colors(
                                    unfocusedContainerColor = boxGrey,
                                    focusedContainerColor = boxGrey,
                                    cursorColor = primaryColor,
                                    disabledLabelColor = boxGrey,
                                    focusedIndicatorColor = boxGrey,
                                    unfocusedIndicatorColor = boxGrey
                                ),
                                shape = RoundedCornerShape(16.dp),
                                onValueChange = {
                                    searchText = it
                                    coroutineScope.launch {
                                        mViewModel.getMealsBySearch(search = it)
                                            .collect { resource ->
                                                mViewModel.requestState.value = resource
                                            }
                                    }
                                },
                                )
                            IconButton(onClick = { isSearching = false }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Hide Search"
                                )
                            }
                        }
                    }

                }

                // Other items in the list
                item {
                    LazyRow(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 8.dp)) {
                        items(mViewModel.createSampleCategoryList().size) {
                            CategoryFilterChip(
                                title = mViewModel.createSampleCategoryList()[it].strCategory,
                                onClick = {
                                    mViewModel.setCategory(it)
                                    coroutineScope.launch {
                                        mViewModel.getMealByCategory(mViewModel.createSampleCategoryList()[it].strCategory)
                                            .collect { it1 ->
                                                mViewModel.requestState.value = it1
                                            }
                                    }
                                },
                                selected = it == selectedIndex
                            )
                        }
                    }
                }

                when (mViewModel.requestState.value) {
                    is Resource.Loading -> {
                        item {
                            LoadingView()
                        }
                    }

                    is Resource.Error -> {
                        item {
                            GeneralErrorScreen()
                        }
                    }

                    is Resource.Success -> {
                        items(mViewModel.state.value.toList().size) { item ->
                            MealCard(meals = mViewModel.state.value[item], navController)
                        }
                    }

                    null -> {}
                    else -> {}
                }
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
        elevation = 10.dp,
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
                        alpha = if (true) 0.5f else 1f
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
                        bottom.linkTo(parent.bottom)
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
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryFilterChip(title: String, selected: Boolean = false, onClick: () -> Unit) {
    FilterChip(
        modifier = Modifier.padding(all = 4.dp),
        selected = selected,
        onClick = onClick,
        colors = ChipDefaults.filterChipColors(backgroundColor = secondaryColorLight, selectedBackgroundColor = secondaryColorDark),
        content = {
            Text(title, style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
        },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected icon",
                    modifier = Modifier.size(18.dp),
                    tint = primaryColorLight
                )
            }
        } else {
            null
        },
    )
}