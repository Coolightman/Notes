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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.util.DISMISS_DELAY
import by.coolightman.notes.util.FRACTIONAL_THRESHOLD
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: NavController, viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isShowSortPanel by rememberSaveable {
        mutableStateOf(false)
    }
    var sortByCash by rememberSaveable {
        mutableStateOf(-1)
    }

//  scroll list to top only when another sortBy
    LaunchedEffect(state.sortByIndex) {
        if (sortByCash != state.sortByIndex) {
            sortByCash = state.sortByIndex
            delay(500)
            listState.animateScrollToItem(0)
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            actions = {
                IconButton(onClick = { isShowSortPanel = !isShowSortPanel }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = "sort",
                        tint = if (isShowSortPanel) {
                            Color.White
                        } else {
                            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        }
                    )
                }

                BadgedIcon(icon = painterResource(id = R.drawable.ic_delete_full_24),
                    iconEmptyBadge = painterResource(id = R.drawable.ic_delete_empty_24),
                    badgeValue = state.trashCount,
                    onClick = {
                        navController.navigate(NavRoutes.NotesTrash.route) {
                            launchSingleTop = true
                        }
                    })
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                }
            })

        SortPanel(
            isVisible = isShowSortPanel, onSort = {
                viewModel.setSortBy(it)
            }, currentSortIndex = state.sortByIndex
        )

        if (state.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_note_24, textId = R.string.no_notes
            )
        } else {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = state.list, key = { it.id }) { note ->
                    val dismissState = rememberDismissState(confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) {
                            scope.launch {
                                delay(DISMISS_DELAY)
                                viewModel.putInNoteTrash(note.id)
                            }
                        }
                        true
                    })

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = { FractionalThreshold(FRACTIONAL_THRESHOLD) },
                        background = {
                            DeleteSwipeSub(
                                dismissState = dismissState,
                                icon = painterResource(R.drawable.ic_delete_sweep_24)
                            )
                        },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        NotesItem(item = note, onClick = {
                            navController.navigate(NavRoutes.EditNote.withArgs(note.id.toString())) {
                                launchSingleTop = true
                            }
                        })
                    }
                }
            }
        }
    }
}