package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.ui.model.BottomTabs
import by.coolightman.notes.ui.theme.GrayContent

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val bottomTabs = remember { BottomTabs.values() }

    val bottomRoutes = remember { BottomTabs.values().map { it.route } }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route ?: "" }
    }

    val isBottomed by remember {
        derivedStateOf { currentRoute in bottomRoutes }
    }

    if (isBottomed) {
        BottomNavigation {
            bottomTabs.forEachIndexed { index, tab ->
                BottomNavigationItem(
                    modifier = Modifier.navigationBarsPadding(),
                    alwaysShowLabel = false,
                    selected = currentRoute == tab.route,
                    icon = {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = stringResource(tab.title),
                            modifier = Modifier.size(STANDARD_ICON_SIZE.dp)
                        )
                    },
                    label = {
                        Text(text = stringResource(tab.title))
                    },
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                if (index < bottomTabs.size - 1) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .width(0.5.dp)
                            .background(GrayContent)
                            .align(CenterVertically)
                    )
                }
            }
        }
    }
}

private const val STANDARD_ICON_SIZE = 24