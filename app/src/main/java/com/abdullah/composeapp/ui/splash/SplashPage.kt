package com.abdullah.composeapp.ui.splash

import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdullah.composeapp.R
import com.abdullah.composeapp.data.navigation.NavTarget
import com.abdullah.composeapp.ui.theme.appSurfaceColor
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashPage(navController: NavController, navigateAfter: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animated_dish))
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(1300)
        coroutineScope.launch {
            navController.navigate(NavTarget.MealsPage.name) {
                popUpTo(NavTarget.SplashPage.name) { inclusive = true }
            }
        }
//        navigateAfter.invoke()
    }

    Surface(
        color = appSurfaceColor
    ){
            LottieAnimation(
                composition, iterations = LottieConstants.IterateForever,
                modifier = Modifier.requiredHeight(250.dp),
                alignment = Alignment.Center,
            )

    }
}