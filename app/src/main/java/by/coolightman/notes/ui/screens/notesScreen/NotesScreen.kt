package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.BadgedIcon
import by.coolightman.notes.ui.components.DeleteSwipeSub
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.util.DISMISS_DELAY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { },
            actions = {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = "sort"
                    )
                }

                BadgedIcon(
                    icon = painterResource(id = R.drawable.ic_delete_full_24),
                    iconEmptyBadge = painterResource(id = R.drawable.ic_delete_empty_24),
                    badgeValue = state.trashCount,
                    onClick = {
                        navController.navigate(NavRoutes.NotesTrash.route) {
                            launchSingleTop = true
                        }
                    })
                IconButton(
                    onClick = { }
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                }
            }
        )

        if (state.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_note_24,
                textId = R.string.no_notes
            )
        } else {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.list,
                    key = { it.id }
                ) { note ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                scope.launch {
                                    delay(DISMISS_DELAY)
                                    viewModel.putInNoteTrash(note.id)
                                }
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = { FractionalThreshold(0.25f) },
                        background = {
                            DeleteSwipeSub(
                                dismissState = dismissState,
                                icon = painterResource(R.drawable.ic_delete_sweep_24)
                            )
                        },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        NotesItem(
                            item = note,
                            onClick = {
                                navController.navigate(NavRoutes.EditNote.withArgs(note.id.toString())) {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }


}