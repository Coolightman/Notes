package by.coolightman.notes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.screens.editNoteScreen.EditNoteScreen
import by.coolightman.notes.ui.screens.editTaskScreen.EditTaskScreen
import by.coolightman.notes.ui.screens.notesScreen.NotesScreen
import by.coolightman.notes.ui.screens.notesTrashScreen.NotesTrashScreen
import by.coolightman.notes.ui.screens.tasksScreen.TasksScreen
import by.coolightman.notes.util.ARG_NOTE_ID
import by.coolightman.notes.util.ARG_TASK_ID

@Composable
fun AppNavigationHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NavRoutes.Notes.route,
        ) {
            NotesScreen(
                navController = navController
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
                navController = navController
            )
        }

        composable(
            route = NavRoutes.NotesTrash.route,
        ) {
            NotesTrashScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.Tasks.route,
        ) {
            TasksScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.EditTask.route + "/{$ARG_TASK_ID}",
            arguments = listOf(
                navArgument(ARG_TASK_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            EditTaskScreen(
                navController = navController
            )
        }
    }
}