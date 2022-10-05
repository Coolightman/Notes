package by.coolightman.notes.ui.screens.tasksScreen

import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.util.DISMISS_DELAY
import by.coolightman.notes.util.FRACTIONAL_THRESHOLD
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val uiState = viewModel.uiState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var openDeleteInactiveTasksDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteInactiveTasksDialog) {
        AppAlertDialog(
            text = stringResource(R.string.delete_inactive_tasks_dialog),
            confirmButtonText = stringResource(R.string.delete),
            onConfirm = {
                viewModel.deleteInactiveTasks()
                openDeleteInactiveTasksDialog = false
            },
            onCancel = { openDeleteInactiveTasksDialog = false }
        )
    }
    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.tasks_title))
            },
            actions = {
                Column(horizontalAlignment = Alignment.End) {
                    CountRow(
                        label = stringResource(R.string.active_count),
                        value = uiState.activeTasksCount
                    )
                    CountRow(
                        label = stringResource(R.string.inactive_count),
                        value = uiState.inactiveTasksCount
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = { isDropMenuExpanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "more",
                        tint = if (isDropMenuExpanded) {
                            Color.White
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
                            openDeleteInactiveTasksDialog = true
                            isDropMenuExpanded = false
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = stringResource(R.string.delete_inactive))
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
                }
            }
        )

        if (uiState.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_task_24, textId = R.string.no_tasks
            )
        } else {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = uiState.list, key = { it.id }) { task ->
                    val dismissState = rememberDismissState(confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) {
                            scope.launch {
                                delay(DISMISS_DELAY)
                                viewModel.deleteTask(task.id)
                                showSnackbar(scaffoldState, context, viewModel, task)
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
                                isNote = false,
                                icon = painterResource(R.drawable.ic_delete_forever_24),
                                subColor = Color.Red
                            )
                        },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        TasksItem(item = task, onClick = {
                            navController.navigate(NavRoutes.EditTask.withArgs(task.id.toString())) {
                                launchSingleTop = true
                            }
                        }, onSwitchActive = { viewModel.switchTaskActivity(task.id) })
                    }
                }
            }
        }
    }
}

private suspend fun showSnackbar(
    scaffoldState: ScaffoldState, context: Context, viewModel: TasksViewModel, task: Task
) {
    val action = scaffoldState.snackbarHostState.showSnackbar(
        message = context.getString(R.string.task_deleted),
        actionLabel = context.getString(R.string.cancel)
    )
    when (action) {
        SnackbarResult.ActionPerformed -> viewModel.cancelDeletion(task.id)
        SnackbarResult.Dismissed -> {}
    }
}