package by.coolightman.notes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            val uiState = viewModel.uiState
            val systemMode = isSystemInDarkTheme()

            val scaffoldState = rememberScaffoldState()
            val navController = rememberAnimatedNavController()

            var themeMode by remember {
                mutableStateOf(true)
            }
            LaunchedEffect(uiState.themeModePreference) {
                themeMode = when (uiState.themeModePreference) {
                    ThemeMode.SYSTEM_MODE -> systemMode
                    ThemeMode.DARK_MODE -> true
                    ThemeMode.LIGHT_MODE -> false
                }
            }

            var isVisibleFAB by remember {
                mutableStateOf(true)
            }

            PrepareUI(darkMode = themeMode) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        AppBottomBar(
                            navController = navController,
                            startScreenPref = uiState.startDestinationPreference
                        )
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
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(MaterialTheme.colors.secondary)
                        )
                        AppNavigationHost(
                            navController = navController,
                            startDestination = NavRoutes.Splash.route,
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
