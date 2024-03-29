package by.coolightman.notes.ui.screens.tasksScreen

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
import androidx.compose.material.icons.filled.Search
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
import by.coolightman.notes.ui.theme.ImportantAction
import by.coolightman.notes.util.dropDownItemColor
import by.coolightman.notes.util.isScrollingUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel(),
    isVisibleFAB: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val view = LocalView.current

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val fabVisibility = listState.isScrollingUp()
    LaunchedEffect(fabVisibility) {
        isVisibleFAB(fabVisibility)
    }

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

    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }

    var selectedCounter by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(uiState.list) {
        selectedCounter = uiState.list.filter { it.isSelected }.size
    }
    var isSelectionMode by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isSelectionMode) {
        if (isSelectionMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        } else {
            viewModel.resetSelections()
        }
    }
    if (isSelectionMode) {
        BackHandler { isSelectionMode = false }
    }
    LaunchedEffect(uiState.list.isEmpty()) {
        isSelectionMode = false
    }

    var openDeleteInactiveTasksDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteInactiveTasksDialog) {
        AppAlertDialog(
            text = stringResource(R.string.delete_inactive_tasks_dialog),
            confirmButtonText = stringResource(R.string.delete),
            confirmButtonColor = ImportantAction,
            onConfirm = {
                viewModel.deleteInactiveTasks()
                openDeleteInactiveTasksDialog = false
            },
            onCancel = { openDeleteInactiveTasksDialog = false }
        )
    }

    var openDeleteSelectedTasksDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteSelectedTasksDialog) {
        AppAlertDialog(
            text = stringResource(R.string.delete_these_tasks_dialog),
            confirmButtonText = stringResource(R.string.delete),
            confirmButtonColor = ImportantAction,
            onConfirm = {
                viewModel.deleteSelectedTasks()
                openDeleteSelectedTasksDialog = false
                isSelectionMode = false
            },
            onCancel = { openDeleteSelectedTasksDialog = false }
        )
    }

    var openUpdateAppDialog by remember(uiState.isShowUpdateAppDialog) {
        mutableStateOf(uiState.isShowUpdateAppDialog)
    }
    if (openUpdateAppDialog) {
        UpdateAppDialog {
            openUpdateAppDialog = false
            viewModel.notShowMoreUpdateDialog()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        if (!isSelectionMode) {
            AppTopAppBar(
                title = { AppTitleText(text = stringResource(R.string.tasks_title)) },
                actions = {
                    if (uiState.list.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                navController.navigate(NavRoutes.SearchTask.route) {
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",
                                tint = MaterialTheme.colors.onSecondary
                            )
                        }
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
                                if (uiState.inactiveTasksCount != 0) {
                                    openDeleteInactiveTasksDialog = true
                                    isDropMenuExpanded = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete",
                                tint = dropDownItemColor()
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.delete_inactive),
                                color = dropDownItemColor()
                            )
                        }

                        if (uiState.isListHasCollapsable) {
                            DropdownMenuItem(
                                onClick = { viewModel.expandAll() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_expand_all_24),
                                    contentDescription = "expand all",
                                    tint = dropDownItemColor()
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = stringResource(R.string.expand_all),
                                    color = dropDownItemColor()
                                )
                            }

                            DropdownMenuItem(
                                onClick = { viewModel.collapseAll() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_collapse_all_24),
                                    contentDescription = "collapse all",
                                    tint = dropDownItemColor()
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = stringResource(R.string.collapse_all),
                                    color = dropDownItemColor()
                                )
                            }
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

                        if (uiState.activeTasksCount != 0 || uiState.inactiveTasksCount != 0) {
                            Divider()

                            CountRow(
                                label = stringResource(R.string.active_count),
                                value = uiState.activeTasksCount,
                                color = dropDownItemColor()
                            )

                            CountRow(
                                label = stringResource(R.string.inactive_count),
                                value = uiState.inactiveTasksCount,
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
                    IconButton(
                        onClick = { viewModel.selectAllTasks() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_select_all_24),
                            contentDescription = "select all",
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }

                    IconButton(
                        onClick = {
                            if (selectedCounter > 0) {
                                openDeleteSelectedTasksDialog = true
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete_forever_24),
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
            filterAlpha = 0.5f,
            onFilterSelection = { viewModel.setFilterSelection(it) }
        )

        if (uiState.list.isEmpty()) {
            EmptyContentSplash(iconId = R.drawable.ic_task_24, textId = R.string.no_tasks)
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = uiState.list, key = { it.id }) { task ->
                    TasksItem(
                        task = task,
                        onClick = {
                            if (task.isActive) {
                                navController.navigate(
                                    NavRoutes.EditTask.withArgs(task.id.toString())
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        },
                        onSwitchActive = { viewModel.switchTaskActivity(task.id) },
                        onLongPress = {
                            scope.launch {
                                viewModel.setCurrentIsSelected(task.id)
                                isSelectionMode = true
                            }
                        },
                        onCheckedChange = { viewModel.switchIsSelected(task.id) },
                        isSelectionMode = isSelectionMode,
                        onCollapseClick = { viewModel.switchCollapse(task.id) },
                        isShowNotificationDate = uiState.isShowNotificationDate,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}
