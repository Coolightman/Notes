package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.ui.viewmodel.EditNoteViewModel

@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditNoteViewModel
) {
    val state = viewModel.state

}