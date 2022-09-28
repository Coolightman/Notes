package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.compose.EmptyContentSplash
import by.coolightman.notes.ui.viewmodel.TasksViewModel

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel
) {
    val state = viewModel.state
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