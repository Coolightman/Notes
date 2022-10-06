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
            DefineSystemBarsColor(darkMode = darkMode)
            content()
        }
    }
}

@Composable
private fun DefineSystemBarsColor(darkMode: Boolean) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkMode
    val statusBarColor = MaterialTheme.colors.background

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }
}