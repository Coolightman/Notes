package by.coolightman.notes.presenter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import by.coolightman.notes.presenter.screen.*
import by.coolightman.notes.ui.model.NavRoute
import by.coolightman.notes.util.ARG_NOTE_ID
import by.coolightman.notes.util.ARG_TASK_ID


@Composable
fun AppNavigationHost(
    navController: NavHostController,
    startDestination: String,
    contentPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NavRoute.Notes.route
        ) {
            NotesScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                modifier = Modifier.padding(contentPadding)
            )
        }

        composable(
            route = NavRoute.EditNote.route + "/{$ARG_NOTE_ID}",
            arguments = listOf(
                navArgument(ARG_NOTE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            EditNoteScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = NavRoute.NotesTrash.route
        ) {
            NotesTrashScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = NavRoute.Tasks.route
        ) {
            TasksScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                modifier = Modifier.padding(contentPadding)
            )
        }

        composable(
            route = NavRoute.EditTask.route + "/{$ARG_TASK_ID}",
            arguments = listOf(
                navArgument(ARG_TASK_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            EditTaskScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
    }
}