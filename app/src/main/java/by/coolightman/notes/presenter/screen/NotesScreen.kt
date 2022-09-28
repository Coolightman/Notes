package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.NotesViewModel
import by.coolightman.notes.ui.compose.NotesScreenSplash

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val state = viewModel.state

    if (state.list.isEmpty()) {
        NotesScreenSplash()
    } else {

    }


}