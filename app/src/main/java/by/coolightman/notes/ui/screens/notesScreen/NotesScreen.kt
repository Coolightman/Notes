package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.DeleteSwipeSub
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem
import by.coolightman.notes.ui.model.NavRoutes

@OptIn(ExperimentalMaterialApi::class)
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(
                items = state.list,
                key = { it.id }
            ) { note ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                    viewModel.putInNoteTrash(note.id)
                }

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd),
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    background = { DeleteSwipeSub(dismissState) }
                ) {
                    NotesItem(
                        item = note,
                        onClick = {
                            navController.navigate(NavRoutes.EditNote.withArgs(note.id.toString()))
                        }
                    )
                }
            }
        }
    }
}