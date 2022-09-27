package by.coolightman.notes.presenter.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello!",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}