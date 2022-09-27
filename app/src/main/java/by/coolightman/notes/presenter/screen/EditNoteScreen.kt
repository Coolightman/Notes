package by.coolightman.notes.presenter.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.coolightman.notes.presenter.state.EditNoteScreenState
import by.coolightman.notes.presenter.viewmodel.EditNoteViewModel

@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditNoteViewModel,
    state: EditNoteScreenState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = state.text,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}