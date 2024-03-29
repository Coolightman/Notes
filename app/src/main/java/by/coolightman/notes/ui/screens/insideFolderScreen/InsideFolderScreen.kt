package by.coolightman.notes.ui.screens.insideFolderScreen

import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.AppTitleText
import by.coolightman.notes.ui.components.AppTopAppBar
import by.coolightman.notes.ui.components.BadgedIcon
import by.coolightman.notes.ui.components.CountRow
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.FAB
import by.coolightman.notes.ui.components.FolderItem
import by.coolightman.notes.ui.components.MoveDialog
import by.coolightman.notes.ui.components.NotesItem
import by.coolightman.notes.ui.components.SelectionTopAppBar
import by.coolightman.notes.ui.components.SortFilterPanel
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.util.dropDownItemColor
import by.coolightman.notes.util.isScrollingUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InsideFolderScreen(
    navController: NavController,
    viewModel: InsideFolderViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val view = LocalView.current

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var isShowSortPanel by remember {
        mutableStateOf(false)
    }

    //  scroll list to top only when another sortBy
    var sortByCash by rememberSaveable {
        mutableStateOf(-1)
    }
    LaunchedEffect(uiState.sortByIndex) {
        if (sortByCash != uiState.sortByIndex) {
            sortByCash = uiState.sortByIndex
            delay(500)
            listState.animateScrollToItem(0)
        }
    }

    val fabVisibility = listState.isScrollingUp()

    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }

    var selectedCounter by remember {
        mutableStateOf(0)
    }
    var selectedFolders by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(uiState.notes, uiState.folders) {
        val notesSelected = uiState.notes.filter { it.isSelected }.size
        val foldersSelected = uiState.folders.filter { it.isSelected }.size
        selectedCounter = notesSelected + foldersSelected
        selectedFolders = foldersSelected
    }
    var isSelectionMode by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isSelectionMode) {
        if (isSelectionMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
            isShowSortPanel = false
        } else {
            viewModel.resetSelections()
        }
    }
    if (isSelectionMode) {
        BackHandler { isSelectionMode = false }
    }
    LaunchedEffect(uiState.notes.isEmpty() && uiState.folders.isEmpty()) {
        isSelectionMode = false
    }

    var isShowMoveDialog by remember {
        mutableStateOf(false)
    }
    if (isShowMoveDialog) {
        MoveDialog(
            destinations = uiState.foldersToMove,
            onClickFolder = {
                viewModel.moveSelectedToFolder(it)
                isShowMoveDialog = false
                isSelectionMode = false
            },
            onCancel = { isShowMoveDialog = false }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (!isSelectionMode) {
                AppTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }
                    },
                    title = { AppTitleText(text = uiState.currentFolderTitle) },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate(
                                    NavRoutes.EditFolder.withArgs(
                                        uiState.currentFolderId.toString(),
                                        "0"
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_edit_24),
                                contentDescription = "edit folder",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }

                        IconButton(onClick = { isShowSortPanel = !isShowSortPanel }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_sort_24),
                                contentDescription = "sort",
                                tint = if (isShowSortPanel) MaterialTheme.colors.primary
                                else MaterialTheme.colors.onSecondary
                            )
                        }

                        IconButton(onClick = { isDropMenuExpanded = true }) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "more",
                                tint = if (isDropMenuExpanded) MaterialTheme.colors.primary
                                else MaterialTheme.colors.onSecondary
                            )
                        }

                        DropdownMenu(
                            expanded = isDropMenuExpanded,
                            onDismissRequest = { isDropMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    isDropMenuExpanded = false
                                    navController.navigate(
                                        NavRoutes.EditFolder.withArgs(
                                            "0",
                                            uiState.currentFolderId.toString()
                                        )
                                    ) {
                                        launchSingleTop = true
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_new_folder_24),
                                    contentDescription = "new folder",
                                    tint = dropDownItemColor()
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = stringResource(R.string.create_folder),
                                    color = dropDownItemColor()
                                )
                            }

                            DropdownMenuItem(
                                onClick = {
                                    navController.navigate(NavRoutes.NotesTrash.route) {
                                        launchSingleTop = true
                                    }
                                    isDropMenuExpanded = false
                                }
                            ) {
                                BadgedIcon(
                                    icon = painterResource(R.drawable.ic_delete_full_24),
                                    iconEmptyBadge = painterResource(R.drawable.ic_delete_empty_24),
                                    badgeValue = uiState.trashCount,
                                    color = dropDownItemColor()
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = stringResource(R.string.trash),
                                    color = dropDownItemColor()
                                )
                            }

                            DropdownMenuItem(
                                onClick = {
                                    navController.navigate(NavRoutes.Settings.route) {
                                        launchSingleTop = true
                                    }
                                    isDropMenuExpanded = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "settings",
                                    tint = dropDownItemColor()
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = stringResource(R.string.settings),
                                    color = dropDownItemColor()
                                )
                            }

                            if (uiState.folders.isNotEmpty() || uiState.notes.isNotEmpty()) {
                                Divider()
                            }

                            if (uiState.folders.isNotEmpty()) {
                                CountRow(
                                    label = stringResource(R.string.total_folders),
                                    value = uiState.folders.size,
                                    color = dropDownItemColor()
                                )
                            }

                            if (uiState.notes.isNotEmpty()) {
                                CountRow(
                                    label = stringResource(R.string.total_notes),
                                    value = uiState.notes.size,
                                    color = dropDownItemColor()
                                )
                            }
                        }
                    }
                )
            } else {
                SelectionTopAppBar(
                    onCloseClick = { isSelectionMode = false },
                    selectedCount = selectedCounter,
                    actions = {
                        IconButton(onClick = { viewModel.selectAllItems() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_select_all_24),
                                contentDescription = "select all",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }

                        IconButton(
                            onClick = {
                                if (selectedCounter > 0 && selectedFolders == 0) {
                                    viewModel.getAllFoldersToMove()
                                    isShowMoveDialog = true
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_move_files_24),
                                contentDescription = "move selected",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }

                        IconButton(
                            onClick = {
                                if (selectedCounter > 0) {
                                    viewModel.putSelectedInTrash()
                                    isSelectionMode = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }
                    }
                )
            }

            SortFilterPanel(
                isVisible = isShowSortPanel,
                currentSortIndex = uiState.sortByIndex,
                onSort = { viewModel.setSortBy(it) },
                currentFilterSelection = uiState.currentFilterSelection,
                onFilterSelection = { viewModel.setFilterSelection(it) }
            )

            if (uiState.notes.isEmpty() && uiState.folders.isEmpty()) {
                EmptyContentSplash(iconId = R.drawable.ic_note_24, textId = R.string.no_notes)
            } else {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = uiState.folders,
                        key = { "${it.id}${it.title}" }) { folder ->
                        FolderItem(
                            folder = folder,
                            isSelectionMode = isSelectionMode,
                            onClick = {
                                navController.navigate(
                                    NavRoutes.InsideFolder.withArgs(folder.id.toString())
                                )
                            },
                            onLongPress = {
                                scope.launch {
                                    viewModel.setCurrentFolderIsSelected(folder.id)
                                    isSelectionMode = true
                                }
                            },
                            onCheckedChange = { viewModel.switchFolderIsSelected(folder.id) },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                    items(items = uiState.notes, key = { it.id }) { note ->
                        NotesItem(
                            note = note,
                            onClick = {
                                navController.navigate(
                                    NavRoutes.EditNote.withArgs(
                                        note.id.toString(),
                                        "0"
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            },
                            onLongPress = {
                                scope.launch {
                                    viewModel.setCurrentIsSelected(note.id)
                                    isSelectionMode = true
                                }
                            },
                            onCheckedChange = { viewModel.switchIsSelected(note.id) },
                            isSelectionMode = isSelectionMode,
                            isShowNoteDate = uiState.isShowNoteDate,
                            isColoredBackground = uiState.isColoredBackground,
                            onCollapseClick = { viewModel.switchCollapse(note.id) },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }
        }
        FAB(
            isVisible = fabVisibility,
            onClick = {
                navController.navigate(
                    NavRoutes.EditNote.withArgs("0", uiState.currentFolderId.toString())
                ) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}
