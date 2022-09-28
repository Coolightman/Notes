package by.coolightman.notes.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.ui.model.BottomTab
import by.coolightman.notes.ui.model.NavRoute

@Composable
fun MainBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomNavList = listOf(BottomTab.NotesTab, BottomTab.TasksTab)

    if (currentRoute == NavRoute.Notes.route
        || currentRoute == NavRoute.Tasks.route
    ) {
        BottomNavigation(modifier = modifier) {
            bottomNavList.forEach { navTab ->
                BottomNavigationItem(
                    alwaysShowLabel = false,
                    selected = currentRoute == navTab.route,
                    onClick = {
                        navController.navigate(navTab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(navTab.icon),
                            contentDescription = stringResource(navTab.title),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(text = stringResource(navTab.title))
                    })
            }
        }
    }
}