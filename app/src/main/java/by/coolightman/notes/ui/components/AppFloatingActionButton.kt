package by.coolightman.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.R
import by.coolightman.notes.ui.model.NavRoutes

@Composable
fun AppFloatingActionButton(
    navController: NavHostController,
    isVisible: Boolean = true
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route ?: "" }
    }
    var isVisibleGenerally by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(currentRoute) {
        isVisibleGenerally =
            currentRoute == NavRoutes.Notes.route || currentRoute == NavRoutes.Tasks.route
    }

    if (isVisibleGenerally) {
        when (currentRoute) {
            NavRoutes.Notes.route -> {
                FAB(
                    isVisible = isVisible,
                    onClick = {
                        navController.navigate(NavRoutes.EditNote.withArgs("0")) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            NavRoutes.Tasks.route -> {
                FAB(
                    isVisible = isVisible,
                    onClick = {
                        navController.navigate(NavRoutes.EditTask.withArgs("0")) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FAB(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally { it * 2 },
        exit = slideOutHorizontally { it * 2 }
    ) {
        FloatingActionButton(
            shape = CircleShape,
            onClick = { onClick() },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_24),
                contentDescription = "add",
                tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
            )
        }
    }
}
