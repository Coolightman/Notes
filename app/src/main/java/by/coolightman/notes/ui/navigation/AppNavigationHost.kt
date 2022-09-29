package by.coolightman.notes.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.screens.editNoteScreen.EditNoteScreen
import by.coolightman.notes.ui.screens.editTaskScreen.EditTaskScreen
import by.coolightman.notes.ui.screens.notesScreen.NotesScreen
import by.coolightman.notes.ui.screens.notesTrashScreen.NotesTrashScreen
import by.coolightman.notes.ui.screens.tasksScreen.TasksScreen
import by.coolightman.notes.util.ARG_NOTE_ID
import by.coolightman.notes.util.ARG_TASK_ID
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


private const val TRANSITION_TIME = 200
private const val SLIDE_TRANSITION_TIME = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController,
    startDestination: String
) {
    AnimatedNavHost(navController = navController, startDestination = startDestination) {

        composable(
            route = NavRoutes.Notes.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoutes.Tasks.route -> slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeIn(animationSpec = tween(TRANSITION_TIME))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoutes.Tasks.route -> slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeOut(animationSpec = tween(0))
                }
            }
        ) {
            NotesScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.EditNote.route + "/{$ARG_NOTE_ID}",
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) },
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
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) }
        ) {
            NotesTrashScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.Tasks.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoutes.Notes.route -> slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeIn(animationSpec = tween(TRANSITION_TIME))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoutes.Notes.route -> slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeOut(animationSpec = tween(0))
                }
            }
        ) {
            TasksScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.EditTask.route + "/{$ARG_TASK_ID}",
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) },
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