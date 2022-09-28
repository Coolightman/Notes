package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.ui.viewmodel.NotesTrashViewModel

@Composable
fun NotesTrashScreen(
    navController: NavController,
    viewModel: NotesTrashViewModel
) {
    val state = viewModel.state

}