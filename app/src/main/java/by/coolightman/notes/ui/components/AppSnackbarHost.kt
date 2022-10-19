package by.coolightman.notes.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(hostState) { data ->
        Snackbar(
            snackbarData = data,
            actionColor = MaterialTheme.colors.primary
        )
    }
}
