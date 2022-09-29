package by.coolightman.notes.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.R
import by.coolightman.notes.ui.model.NavRoutes

@Composable
fun AppFloatingActionButton(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route ?: "" }
    }

    when (currentRoute) {
        NavRoutes.Notes.route -> {
            NotesFAB(navController)
        }
        NavRoutes.Tasks.route -> {
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
            navController.navigate(NavRoutes.EditNote.withArgs("0")) {
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
            navController.navigate(NavRoutes.EditTask.withArgs("0")) {
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