package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.EditTaskViewModel

@Composable
fun EditTaskScreen(
    navController: NavController,
    viewModel: EditTaskViewModel
) {
    val state = viewModel.state

}