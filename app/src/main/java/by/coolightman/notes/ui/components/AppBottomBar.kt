package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.coolightman.notes.ui.model.BottomTabs

@Composable
fun AppBottomBar(
    navController: NavHostController,
    startScreenPref: String
) {
    val bottomTabs = remember { BottomTabs.values() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route ?: "" }
    }
    val bottomRoutes = remember { bottomTabs.map { it.route } }
    var isVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(currentRoute) {
        isVisible = currentRoute in bottomRoutes
    }

    if (isVisible) {
        BottomNavigation {
            bottomTabs.forEach { tab ->
                BottomNavigationItem(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .background(MaterialTheme.colors.secondary),
                    selected = currentRoute == tab.route,
                    icon = {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = stringResource(tab.title)
                        )
                    },
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(startScreenPref) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.8f),
                )
            }
        }
    }
}