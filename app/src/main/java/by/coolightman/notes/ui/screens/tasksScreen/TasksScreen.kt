package by.coolightman.notes.ui.screens.tasksScreen

import androidx.compose.runtime.Composable
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

    if (state.list.isEmpty()) {
        EmptyContentSplash(
            iconId = R.drawable.ic_task_24,
            textId = R.string.no_tasks
        )
    } else {

    }
}