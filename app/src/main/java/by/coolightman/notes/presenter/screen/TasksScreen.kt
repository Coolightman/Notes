package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.TasksViewModel
import by.coolightman.notes.ui.compose.TasksScreenSplash

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state

    if (state.list.isEmpty()) {
        TasksScreenSplash(modifier = modifier)
    } else {

    }
}