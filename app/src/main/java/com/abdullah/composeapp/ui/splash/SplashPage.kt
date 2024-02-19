package com.abdullah.composeapp.ui.splash

import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdullah.composeapp.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun SplashPage(navigateAfter: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animated_dish))

    LaunchedEffect(Unit) {
        delay(1300)
        navigateAfter.invoke()
    }

    Surface{
            LottieAnimation(
                composition, iterations = LottieConstants.IterateForever,
                modifier = Modifier.requiredHeight(250.dp),
                alignment = Alignment.Center,
            )

    }
}