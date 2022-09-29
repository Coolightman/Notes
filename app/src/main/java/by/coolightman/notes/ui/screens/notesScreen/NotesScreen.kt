package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    if (state.list.isEmpty()) {
        EmptyContentSplash(
            iconId = R.drawable.ic_outline_note_64,
            textId = R.string.no_notes
        )
    } else {
        LazyColumn {
            items(
                items = state.list,
                key = { note -> note.id }
            ) { note ->
                NotesItem(item = note)
            }
        }
    }
}