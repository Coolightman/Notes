package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.viewmodel.TasksViewModel
import by.coolightman.notes.ui.compose.EmptyContentSplash

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel
) {
    val state = viewModel.state

    if (state.list.isEmpty()) {
        EmptyContentSplash(
            iconId = R.drawable.ic_baseline_task_alt_64,
            textId = R.string.no_tasks
        )
    } else {

    }
}