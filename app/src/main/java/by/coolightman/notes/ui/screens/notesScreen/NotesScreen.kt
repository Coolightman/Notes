package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    val uiState = viewModel.uiState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isShowSortPanel by rememberSaveable {
        mutableStateOf(false)
    }
    var sortByCash by rememberSaveable {
        mutableStateOf(-1)
    }

//  scroll list to top only when another sortBy
    LaunchedEffect(uiState.sortByIndex) {
        if (sortByCash != uiState.sortByIndex) {
            sortByCash = uiState.sortByIndex
            delay(500)
            listState.animateScrollToItem(0)
        }
    }
    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = {
                AppTitleText(text = stringResource(id = R.string.notes_title))
            },
            actions = {
                IconButton(onClick = { isShowSortPanel = !isShowSortPanel }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = "sort",
                        tint = if (isShowSortPanel) {
                            MaterialTheme.colors.primaryVariant
                        } else {
                            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        }
                    )
                }
                IconButton(onClick = { isDropMenuExpanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "more",
                        tint = if (isDropMenuExpanded) {
                            MaterialTheme.colors.primaryVariant
                        } else {
                            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        }
                    )
                }
                DropdownMenu(
                    expanded = isDropMenuExpanded,
                    onDismissRequest = { isDropMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate(NavRoutes.NotesTrash.route) {
                                launchSingleTop = true
                            }
                            isDropMenuExpanded = false
                        }
                    ) {
                        BadgedIcon(
                            icon = painterResource(id = R.drawable.ic_delete_full_24),
                            iconEmptyBadge = painterResource(id = R.drawable.ic_delete_empty_24),
                            badgeValue = uiState.trashCount
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.trash))
                    }
                    DropdownMenuItem(onClick = {
                        navController.navigate(NavRoutes.Settings.route) {
                            launchSingleTop = true
                        }
                        isDropMenuExpanded = false
                    }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = stringResource(R.string.settings))
                    }
                    Divider()
                    CountRow(
                        label = stringResource(R.string.total_count),
                        value = uiState.notesCount
                    )
                }
            })

        SortPanel(
            isVisible = isShowSortPanel,
            onSort = {
                viewModel.setSortBy(it)
            },
            currentSortIndex = uiState.sortByIndex
        )

        if (uiState.list.isEmpty()) {
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
                items(items = uiState.list, key = { it.id }) { note ->
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
                                icon = painterResource(R.drawable.ic_delete_sweep_24),
                                subColor = Color.Yellow
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