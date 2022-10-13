package by.coolightman.notes.ui.screens.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import by.coolightman.notes.ui.MainViewModel
import by.coolightman.notes.ui.model.NavRoutes

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {

    val startDestination by remember(viewModel.uiState.startDestinationPreference) {
        mutableStateOf(viewModel.uiState.startDestinationPreference)
    }

    LaunchedEffect(startDestination) {
        if (startDestination != NavRoutes.Splash.route) {
            navController.navigate(startDestination) {
                popUpTo(NavRoutes.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    )
}