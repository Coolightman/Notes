package by.coolightman.notes.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
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

private const val TRANSITION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController,
    startDestination: String,
    scaffoldState: ScaffoldState,
    isVisibleFAB: (Boolean) -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val displayWidth by remember {
        mutableStateOf(
            with(density){configuration.screenWidthDp.dp.toPx().toInt()}
        )
    }
    val displayHeight by remember {
        mutableStateOf(
            with(density){configuration.screenHeightDp.dp.toPx().toInt()}
        )
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        composable(
            route = NavRoutes.Splash.route,
            enterTransition = { fadeIn(animationSpec = tween(10)) },
            exitTransition = { fadeOut(animationSpec = tween(10)) },
        ) {
            SplashScreen()
        }

        composable(
            route = NavRoutes.Notes.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavRoutes.Tasks.route -> {
                        slideInHorizontally(initialOffsetX = { -displayWidth })
                    }
                    else -> fadeIn(animationSpec = tween(TRANSITION_DURATION))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoutes.Tasks.route -> {
                        slideOutHorizontally(targetOffsetX = { -displayWidth })
                    }
                    else -> fadeOut(animationSpec = tween(TRANSITION_DURATION))
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
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
            arguments = listOf(
                navArgument(ARG_NOTE_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            EditNoteScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = NavRoutes.NotesTrash.route,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) }
        ) {
            NotesTrashScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.SearchNote.route,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) }
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
                        slideInHorizontally(initialOffsetX = { displayWidth })
                    }
                    else -> fadeIn(animationSpec = tween(TRANSITION_DURATION))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavRoutes.Notes.route -> {
                        slideOutHorizontally(targetOffsetX = { displayWidth })
                    }
                    else -> fadeOut(animationSpec = tween(TRANSITION_DURATION))
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
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) },
            arguments = listOf(
                navArgument(ARG_TASK_ID) {
                    type = NavType.LongType
                }
            )
        ) {
            EditTaskScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }

        composable(
            route = NavRoutes.SearchTask.route,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) }
        ) {
            SearchTaskScreen(
                navController = navController
            )
        }

        composable(
            route = NavRoutes.Settings.route,
            enterTransition = { fadeIn(animationSpec = tween(TRANSITION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(TRANSITION_DURATION)) }
        ) {
            SettingsScreen(
                navController = navController
            )
        }
    }
}