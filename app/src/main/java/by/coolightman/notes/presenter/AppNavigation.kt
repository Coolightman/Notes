package by.coolightman.notes.presenter

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import by.coolightman.notes.presenter.screen.EditNoteScreen
import by.coolightman.notes.presenter.screen.NotesScreen
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.util.ARG_NOTE_ID


@Composable
fun AppNavigation(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NavRoutes.Notes.route
        ) {
            NotesScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = NavRoutes.EditNote.route + "/{$ARG_NOTE_ID}",
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
    }
}