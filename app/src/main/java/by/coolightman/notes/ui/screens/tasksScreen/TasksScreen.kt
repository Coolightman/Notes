package by.coolightman.notes.ui.screens.tasksScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.EmptyContentSplash

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val isEmptyScreen = remember {
        derivedStateOf { state.list.isEmpty() }
    }

    if (isEmptyScreen.value) {
        EmptyContentSplash(
            iconId = R.drawable.ic_baseline_task_alt_64,
            textId = R.string.no_tasks
        )
    } else {

    }
}