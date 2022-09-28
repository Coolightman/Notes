package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.EditNoteViewModel

@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditNoteViewModel
) {
    val state = viewModel.state

}