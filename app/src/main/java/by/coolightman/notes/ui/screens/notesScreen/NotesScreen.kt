package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem
import kotlinx.coroutines.delay

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    var isSplashVisible by remember {
        mutableStateOf(false)
    }

    if (state.list.isEmpty()) {
        LaunchedEffect(Unit){
            delay(200)
            isSplashVisible = true
        }
        if (isSplashVisible) {
            EmptyContentSplash(
                iconId = R.drawable.ic_outline_note_64,
                textId = R.string.no_notes
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(
                items = state.list,
                key = { note -> note.id }
            ) { note ->
                NotesItem(item = note)
            }
        }
    }
}