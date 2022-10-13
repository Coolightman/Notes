package by.coolightman.notes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import by.coolightman.notes.ui.components.AppBottomBar
import by.coolightman.notes.ui.components.AppFloatingActionButton
import by.coolightman.notes.ui.components.AppSnackbarHost
import by.coolightman.notes.ui.components.PrepareUI
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode
import by.coolightman.notes.ui.navigation.AppNavigationHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val systemMode = isSystemInDarkTheme()
            var themeMode by remember {
                mutableStateOf(systemMode)
            }
            LaunchedEffect(viewModel.uiState.themeModePreference) {
                themeMode = when (viewModel.uiState.themeModePreference) {
                    ThemeMode.SYSTEM_MODE -> systemMode
                    ThemeMode.DARK_MODE -> true
                    ThemeMode.LIGHT_MODE -> false
                }
            }
            var startDestination by remember {
                mutableStateOf(NavRoutes.Splash.route)
            }
            LaunchedEffect(viewModel.uiState.startDestinationPreference) {
                startDestination = viewModel.uiState.startDestinationPreference
            }
            var isVisibleFAB by remember {
                mutableStateOf(true)
            }
            PrepareUI(darkMode = themeMode) {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberAnimatedNavController()
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        AppBottomBar(navController = navController)
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        AppFloatingActionButton(
                            navController = navController,
                            isVisible = isVisibleFAB
                        )
                    },
                    snackbarHost = { AppSnackbarHost(hostState = it) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        AppNavigationHost(
                            navController = navController,
                            startDestination = startDestination,
                            scaffoldState = scaffoldState,
                            isVisibleFAB = {
                                isVisibleFAB = it
                            }
                        )
                    }
                }
            }
        }
    }
}
