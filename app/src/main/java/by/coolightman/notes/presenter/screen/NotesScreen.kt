package by.coolightman.notes.presenter.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.coolightman.notes.presenter.viewmodel.NotesViewModel
import by.coolightman.notes.ui.compose.NotesScreenSplash

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state

    if (state.list.isEmpty()) {
        NotesScreenSplash(modifier = modifier)
    } else {

    }


}