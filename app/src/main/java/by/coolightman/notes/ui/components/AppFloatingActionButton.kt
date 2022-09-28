package by.coolightman.notes.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.R
import by.coolightman.notes.ui.model.NavRoute

@Composable
fun AppFloatingActionButton(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        NavRoute.Notes.route -> {
            NotesFAB(navController)
        }
        NavRoute.Tasks.route -> {
            TasksFAB(navController)
        }
    }
}

@Composable
fun NotesFAB(
    navController: NavHostController
) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            navController.navigate(NavRoute.EditNote.withArgs("0")) {
                launchSingleTop = true
            }
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_note_add_24),
            contentDescription = "add note"
        )
    }
}

@Composable
fun TasksFAB(
    navController: NavHostController
) {
    FloatingActionButton(
        shape = CircleShape,
        onClick = {
            navController.navigate(NavRoute.EditTask.withArgs("0")) {
                launchSingleTop = true
            }
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_add_task_24),
            contentDescription = "add note"
        )
    }
}