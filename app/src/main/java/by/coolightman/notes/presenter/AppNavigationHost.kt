package by.coolightman.notes.presenter

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import by.coolightman.notes.presenter.screen.*
import by.coolightman.notes.ui.model.NavRoute
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
            route = NavRoute.Notes.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoute.Tasks.route -> slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeIn(animationSpec = tween(TRANSITION_TIME))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoute.Tasks.route -> slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeOut(animationSpec = tween(TRANSITION_TIME))
                }
            }
        ) {
            NotesScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = NavRoute.EditNote.route + "/{$ARG_NOTE_ID}",
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) },
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
            route = NavRoute.NotesTrash.route,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) }
        ) {
            NotesTrashScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = NavRoute.Tasks.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoute.Notes.route -> slideIntoContainer(
                        towards = AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeIn(animationSpec = tween(TRANSITION_TIME))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoute.Notes.route -> slideOutOfContainer(
                        towards = AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(SLIDE_TRANSITION_TIME)
                    )
                    else -> fadeOut(animationSpec = tween(TRANSITION_TIME))
                }
            }
        ) {
            TasksScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = NavRoute.EditTask.route + "/{$ARG_TASK_ID}",
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_TIME)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_TIME)) },
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