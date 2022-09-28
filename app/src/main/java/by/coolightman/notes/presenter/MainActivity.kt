package by.coolightman.notes.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import by.coolightman.notes.ui.compose.MainBottomBar
import by.coolightman.notes.ui.compose.PrepareUI
import by.coolightman.notes.ui.model.StartDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrepareUI {
                val navController = rememberNavController()
                val startDestination = StartDestination.NOTES.route
                Scaffold(
                    bottomBar = {
                        MainBottomBar(navController = navController)
                    }
                ) { contentPadding ->
                    Box(modifier = Modifier.padding(contentPadding)) {
                        AppNavigationHost(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }
                }
            }
        }
    }
}
