package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.presenter.viewmodel.TasksViewModel
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
            textId = R.string.tasks_title
        )
    } else {

    }
}