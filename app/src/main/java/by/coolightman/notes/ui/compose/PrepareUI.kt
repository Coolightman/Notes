package by.coolightman.notes.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import by.coolightman.notes.ui.theme.NotesTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PrepareUI(content: @Composable () -> Unit) {
    NotesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DefineSystemBarsColor()
            content()
        }
    }
}

@Composable
private fun DefineSystemBarsColor() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val statusBarColor = MaterialTheme.colors.background

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
    }
}