package by.coolightman.notes.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import by.coolightman.notes.R
import by.coolightman.notes.ui.model.NavRoutes
import kotlinx.coroutines.delay

private const val SPLASH_DELAY_MILLIS = 250L
private const val ANIM_DURATION_MILLIS = 250
private const val ANIM_OVERSHOOT = 1.2f

@Composable
fun SplashScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(viewModel.uiState.startDestinationPreference) {
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(
                durationMillis = ANIM_DURATION_MILLIS,
                easing = {
                    OvershootInterpolator(ANIM_OVERSHOOT).getInterpolation(it)
                }
            )
        )
        delay(SPLASH_DELAY_MILLIS)

        navController.navigate(viewModel.uiState.startDestinationPreference) {
            popUpTo(NavRoutes.Splash.route) {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "splash",
            modifier = Modifier
                .align(Alignment.Center)
                .size(80.dp)
                .scale(scale.value)

        )
    }
}