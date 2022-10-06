package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.ui.model.BottomTabs

@Composable
fun AppBottomBar(
    navController: NavHostController,
) {
    val bottomTabs = remember { BottomTabs.values() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route ?: "" }
    }

    val bottomRoutes = remember { bottomTabs.map { it.route } }
    val isBottomed by remember {
        derivedStateOf { currentRoute in bottomRoutes }
    }

    if (isBottomed) {
        BottomNavigation {
            bottomTabs.forEach { tab ->
                BottomNavigationItem(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .background(MaterialTheme.colors.background),
                    selected = currentRoute == tab.route,
                    icon = {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = stringResource(tab.title)
                        )
                    },
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = MaterialTheme.colors.primaryVariant,
                    unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.8f),
                )
            }
        }
    }
}