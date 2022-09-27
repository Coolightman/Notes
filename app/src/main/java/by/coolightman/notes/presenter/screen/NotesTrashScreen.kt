package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.NotesTrashViewModel
import by.coolightman.notes.presenter.viewmodel.NotesViewModel

@Composable
fun NotesTrashScreen(
    navController: NavController,
    viewModel: NotesTrashViewModel
) {
    val state = viewModel.state

}