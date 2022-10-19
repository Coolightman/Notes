package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import by.coolightman.notes.ui.theme.NotesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PrepareUI(
    darkMode: Boolean,
    content: @Composable () -> Unit
) {
    NotesTheme(darkTheme = darkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DefineSystemBarsColors(darkMode = darkMode)
            content()
        }
    }
}

@Composable
private fun DefineSystemBarsColors(darkMode: Boolean) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkMode
    val statusBarColor = MaterialTheme.colors.secondary
    val navigationBarColor = MaterialTheme.colors.background

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = useDarkIcons
        )
    }
}
