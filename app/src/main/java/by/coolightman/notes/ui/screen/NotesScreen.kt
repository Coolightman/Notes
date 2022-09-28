package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.viewmodel.NotesViewModel
import by.coolightman.notes.ui.compose.EmptyContentSplash

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {
    val state = viewModel.state

    if (state.list.isEmpty()) {
        EmptyContentSplash(
            iconId = R.drawable.ic_outline_note_64,
            textId = R.string.no_notes
        )
    } else {

    }


}