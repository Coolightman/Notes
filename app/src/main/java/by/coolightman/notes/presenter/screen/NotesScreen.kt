package by.coolightman.notes.presenter.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.coolightman.notes.presenter.state.NotesScreenState
import by.coolightman.notes.presenter.viewmodel.NotesViewModel
import by.coolightman.notes.ui.model.Screen

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel,
    state: NotesScreenState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                viewModel.onAction()
                navController.navigate(Screen.EditNote.withArgs("13"))
            }) {
            Text(text = "Go to edit")
        }
        Text(
            text = "${state.list.size}",
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}