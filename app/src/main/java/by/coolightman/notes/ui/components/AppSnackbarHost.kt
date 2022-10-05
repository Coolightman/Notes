package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState
) {
    SnackbarHost(hostState) { data ->
        Snackbar(
            snackbarData = data,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}