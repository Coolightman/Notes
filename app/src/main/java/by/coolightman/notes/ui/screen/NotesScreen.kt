package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.compose.EmptyContentSplash
import by.coolightman.notes.ui.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val state = viewModel.state
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