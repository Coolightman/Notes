package by.coolightman.notes.ui.screens.notesScreen

import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.ui.model.NavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: NavController, viewModel: NotesViewModel = hiltViewModel(),
    isVisibleFAB: (Boolean) -> Unit
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
    val fabVisibility = listState.isScrollingUp()
    LaunchedEffect(fabVisibility) {
        isVisibleFAB(fabVisibility)
    }
    var isSelectionMode by remember {
        mutableStateOf(false)
    }
    val view = LocalView.current
    LaunchedEffect(isSelectionMode) {
        if (isSelectionMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        }
    }
    if (isSelectionMode) {
        BackHandler {
            isSelectionMode = false
        }
    }
    LaunchedEffect(uiState.list.isEmpty()){
        isSelectionMode = false
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (!isSelectionMode) {
            AppTopAppBar(
                title = {
                    AppTitleText(text = stringResource(id = R.string.notes_title))
                },
                actions = {
                    if (uiState.list.isNotEmpty()) {
                        IconButton(onClick = { isShowSortPanel = !isShowSortPanel }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                                contentDescription = "sort",
                                tint = if (isShowSortPanel) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                                }
                            )
                        }
                    }
                    IconButton(onClick = { isDropMenuExpanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "more",
                            tint = if (isDropMenuExpanded) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
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
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "settings"
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = stringResource(R.string.settings))
                        }
                        if (uiState.notesCount != 0) {
                            Divider()
                            CountRow(
                                label = stringResource(R.string.total_count),
                                value = uiState.notesCount
                            )
                        }
                    }
                })
        } else {
            SelectionTopAppBar(
                onCloseClick = { isSelectionMode = false },
                selectedCount = uiState.selectedCount,
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.selectAllNotes()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_select_all_24),
                            contentDescription = "select all",
                            tint = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.putSelectedNotesInTrash()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                        )
                    }
                }
            )
        }

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
                    NotesItem(
                        note = note,
                        onClick = {
                            navController.navigate(NavRoutes.EditNote.withArgs(note.id.toString())) {
                                launchSingleTop = true
                            }
                        },
                        onLongPress = {
                            scope.launch {
                                viewModel.resetSelections(note.id)
                                delay(50)
                                isSelectionMode = true
                            }
                        },
                        onCheckedChange = { viewModel.setIsSelectedNote(note.id) },
                        isSelectionMode = isSelectionMode,
                        isShowNoteDate = uiState.isShowNoteDate,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}