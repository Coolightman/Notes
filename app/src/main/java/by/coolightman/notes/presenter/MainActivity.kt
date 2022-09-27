package by.coolightman.notes.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.coolightman.notes.presenter.screen.EditNoteScreen
import by.coolightman.notes.presenter.screen.NotesScreen
import by.coolightman.notes.presenter.viewmodel.EditNoteViewModel
import by.coolightman.notes.presenter.viewmodel.NotesViewModel
import by.coolightman.notes.ui.compose.PrepareUI
import by.coolightman.notes.ui.model.Screen
import by.coolightman.notes.util.ARG_NOTE_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrepareUI {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.Notes.route) {

                    composable(route = Screen.Notes.route) {
                        val viewModel = hiltViewModel<NotesViewModel>()
                        val state = viewModel.state
                        NotesScreen(
                            navController = navController,
                            viewModel = viewModel,
                            state = state
                        )
                    }

                    composable(
                        route = Screen.EditNote.route + "/{$ARG_NOTE_ID}",
                        arguments = listOf(
                            navArgument(ARG_NOTE_ID) {
                                type = NavType.LongType
                            }
                        )
                    ) {
                        val viewModel = hiltViewModel<EditNoteViewModel>()
                        val state = viewModel.state
                        EditNoteScreen(
                            navController = navController,
                            viewModel = viewModel,
                            state = state
                        )
                    }
                }
            }
        }
    }
}
