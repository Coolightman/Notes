package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.TasksViewModel

@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: TasksViewModel
) {
    val state = viewModel.state

}