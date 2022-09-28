package by.coolightman.notes.ui.screens.notesTrashScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NotesTrashScreen(
    navController: NavController,
    viewModel: NotesTrashViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

}