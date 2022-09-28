package by.coolightman.notes.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import by.coolightman.notes.ui.viewmodel.EditTaskViewModel

@Composable
fun EditTaskScreen(
    navController: NavController,
    viewModel: EditTaskViewModel
) {
    val state = viewModel.state

}