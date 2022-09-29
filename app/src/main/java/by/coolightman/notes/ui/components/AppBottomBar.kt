package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.ui.model.BottomTab
import by.coolightman.notes.ui.theme.GrayContent

@Composable
fun AppBottomBar(
    navController: NavHostController
) {
    val bottomNavList = remember {
        listOf(BottomTab.Notes, BottomTab.Tasks)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route ?: "" }
    }

    val isBottomed by remember {
        derivedStateOf { currentRoute == BottomTab.Notes.route || currentRoute == BottomTab.Tasks.route }
    }

    if (isBottomed) {
        BottomNavigation(
            modifier = Modifier.clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
        ) {
            bottomNavList.forEachIndexed { index, tab ->
                BottomNavigationItem(
                    alwaysShowLabel = false,
                    selected = currentRoute == tab.route,
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = stringResource(tab.title),
                            modifier = Modifier.size(STANDARD_ICON_SIZE.dp)
                        )
                    },
                    label = {
                        Text(text = stringResource(tab.title))
                    }
                )
                if (index < bottomNavList.size - 1) {
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