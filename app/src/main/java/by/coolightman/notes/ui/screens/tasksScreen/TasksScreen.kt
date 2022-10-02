package by.coolightman.notes.ui.screens.tasksScreen

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.DeleteSwipeSub
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.TasksItem
import by.coolightman.notes.ui.model.NavRoutes

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { },
            actions = {
                IconButton(
                    onClick = { }
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                }
            }
        )

        if (state.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_task_24,
                textId = R.string.no_tasks
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
                ) { task ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                viewModel.deleteTask(task.id)
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
                                icon = painterResource(R.drawable.ic_delete_forever_24)
                            )
                        },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        TasksItem(
                            item = task,
                            onClick = {
                                navController.navigate(NavRoutes.EditTask.withArgs(task.id.toString())) {
                                    launchSingleTop = true
                                }
                            },
                            onSwitchActive = { viewModel.switchTaskActivity(task.id) }
                        )
                    }
                }
            }

        }
    }
}