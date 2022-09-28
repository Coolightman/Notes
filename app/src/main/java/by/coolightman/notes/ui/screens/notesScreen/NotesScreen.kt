package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.EmptyContentSplash

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val isEmptyScreen = remember {
        derivedStateOf { state.list.isEmpty() }
    }

    if (isEmptyScreen.value) {
        EmptyContentSplash(
            iconId = R.drawable.ic_outline_note_64,
            textId = R.string.no_notes
        )
    } else {

    }


}