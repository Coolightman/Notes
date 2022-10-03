package by.coolightman.notes.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import by.coolightman.notes.ui.components.AppBottomBar
import by.coolightman.notes.ui.components.AppFloatingActionButton
import by.coolightman.notes.ui.components.AppSnackbarHost
import by.coolightman.notes.ui.components.PrepareUI
import by.coolightman.notes.ui.model.StartDestination
import by.coolightman.notes.ui.navigation.AppNavigationHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            PrepareUI {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val startDestination = StartDestination.NOTES.route
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        AppBottomBar(navController = navController)
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        AppFloatingActionButton(navController = navController)
                    },
                    snackbarHost = { AppSnackbarHost(hostState = it) }
                ) { contentPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                    ) {
                        AppNavigationHost(
                            navController = navController,
                            startDestination = startDestination,
                            scaffoldState = scaffoldState
                        )
                    }
                }
            }
        }
    }
}
