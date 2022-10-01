package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.BadgedIcon
import by.coolightman.notes.ui.components.DeleteSwipeSub
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem
import by.coolightman.notes.ui.model.NavRoutes

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.notes_title)) },
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.list,
                    key = { it.id }
                ) { note ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                viewModel.putInNoteTrash(note.id)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = { FractionalThreshold(0.2f) },
                        background = { DeleteSwipeSub(dismissState) },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        val elevation = animateDpAsState(
                            targetValue = if (dismissState.dismissDirection != null) 10.dp else 2.dp,
                            animationSpec = tween(100)
                        )

                        NotesItem(
                            item = note,
                            elevation = elevation.value,
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