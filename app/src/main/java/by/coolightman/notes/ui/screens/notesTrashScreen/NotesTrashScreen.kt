package by.coolightman.notes.ui.screens.notesTrashScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.DeleteRestoreSwipeSub
import by.coolightman.notes.ui.components.DeleteSwipeSub
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem
import by.coolightman.notes.ui.model.NavRoutes

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesTrashScreen(
    navController: NavController,
    viewModel: NotesTrashViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    if (state.list.isEmpty()) {
        EmptyContentSplash(
            iconId = R.drawable.ic_baseline_delete_outline_24,
            textId = R.string.no_trash
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                title = { Text(text = stringResource(id = R.string.notes_trash_title)) },
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_restore_from_trash_24),
                            contentDescription = "restore all"
                        )
                    }
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete_forever_24),
                            contentDescription = "delete all forever"
                        )
                    }
                }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    items = state.list,
                    key = { it.id }
                ) { note ->
                    val dismissState = rememberDismissState()

                    when {
                        dismissState.isDismissed(DismissDirection.StartToEnd) -> {
                            viewModel.deleteNote(note.id)
                        }
                        dismissState.isDismissed(DismissDirection.EndToStart) -> {
                            viewModel.restoreNote(note.id)
                        }
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(
                            DismissDirection.StartToEnd,
                            DismissDirection.EndToStart
                        ),
                        dismissThresholds = { FractionalThreshold(0.2f) },
                        background = { DeleteRestoreSwipeSub(dismissState) },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        NotesItem(
                            item = note,
                            onClick = {}
                        )
                    }
                }
            }
        }

    }
}