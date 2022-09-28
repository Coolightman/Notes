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

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val bottomNavList = listOf(BottomTab.Notes, BottomTab.Tasks)

    if (currentRoute == BottomTab.Notes.route
        || currentRoute == BottomTab.Tasks.route
    ) {
        BottomNavigation {
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
                            modifier = Modifier.size(STANDARD_ICON_SIZE.dp)
                        )
                    },
                    label = {
                        Text(text = stringResource(navTab.title))
                    })
            }
        }
    }
}

private const val STANDARD_ICON_SIZE = 24