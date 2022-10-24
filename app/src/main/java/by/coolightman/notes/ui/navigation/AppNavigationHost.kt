package by.coolightman.notes.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.screens.editNoteScreen.EditNoteScreen
import by.coolightman.notes.ui.screens.editTaskScreen.EditTaskScreen
import by.coolightman.notes.ui.screens.notesScreen.NotesScreen
import by.coolightman.notes.ui.screens.notesTrashScreen.NotesTrashScreen
import by.coolightman.notes.ui.screens.searchNoteScreen.SearchNoteScreen
import by.coolightman.notes.ui.screens.searchTaskScreen.SearchTaskScreen
import by.coolightman.notes.ui.screens.settingsScreen.SettingsScreen
import by.coolightman.notes.ui.screens.splashScreen.SplashScreen
import by.coolightman.notes.ui.screens.tasksScreen.TasksScreen
import by.coolightman.notes.util.ARG_NOTE_ID
import by.coolightman.notes.util.ARG_TASK_ID
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

private const val SLIDE_FADE_DURATION = 200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController,
    startDestination: String,
    scaffoldState: ScaffoldState,
    isVisibleFAB: (Boolean) -> Unit
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = NavRoutes.Splash.route,
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            SplashScreen(navController)
        }

        composable(
            route = NavRoutes.Notes.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoutes.Tasks.route -> {
                        slideInHorizontally(initialOffsetX = { -it }) +
                                fadeIn(tween(SLIDE_FADE_DURATION))
                    }
                    else -> fadeIn(animationSpec = tween())
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoutes.Tasks.route -> {
                        slideOutHorizontally(targetOffsetX = { -it }) +
                                fadeOut(tween(SLIDE_FADE_DURATION))
                    }
                    else -> fadeOut(animationSpec = tween())
                }
            }
        ) {
            NotesScreen(
                navController = navController,
                scaffoldState = scaffoldState,
                isVisibleFAB = { isVisibleFAB(it) }
            )
        }

        composable(
            route = NavRoutes.EditNote.route + "/{$ARG_NOTE_ID}",
            arguments = listOf(
                navArgument(ARG_NOTE_ID) {
                    type = NavType.LongType
                }
            ),
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            EditNoteScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = NavRoutes.NotesTrash.route,
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            NotesTrashScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = NavRoutes.SearchNote.route,
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            SearchNoteScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.Tasks.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoutes.Notes.route -> {
                        slideInHorizontally(initialOffsetX = { it }) +
                                fadeIn(tween(SLIDE_FADE_DURATION))
                    }
                    else -> fadeIn(animationSpec = tween())
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoutes.Notes.route -> {
                        slideOutHorizontally(targetOffsetX = { it }) +
                                fadeOut(tween(SLIDE_FADE_DURATION))
                    }
                    else -> fadeOut(animationSpec = tween())
                }
            }
        ) {
            TasksScreen(
                navController = navController,
                isVisibleFAB = { isVisibleFAB(it) }
            )
        }

        composable(
            route = NavRoutes.EditTask.route + "/{$ARG_TASK_ID}",
            arguments = listOf(
                navArgument(ARG_TASK_ID) {
                    type = NavType.LongType
                }
            ),
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            EditTaskScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = NavRoutes.SearchTask.route,
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            SearchTaskScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.Settings.route,
            enterTransition = { fadeIn(animationSpec = tween()) },
            exitTransition = { fadeOut(animationSpec = tween()) }
        ) {
            SettingsScreen(
                navController = navController
            )
        }
    }
}
