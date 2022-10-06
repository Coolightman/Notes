package by.coolightman.notes.ui.screens.settingsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.AppTopAppBar
import by.coolightman.notes.ui.model.NavRoutes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = {
                Text(text = stringResource(id = R.string.settings))
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Card(
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Start screen by default:")
                    Row(Modifier.fillMaxWidth()) {
                        FilterChip(
                            selected  = uiState.appStartDestination == NavRoutes.Notes.route,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_note_24),
                                    contentDescription = ""
                                )
                            },
                            onClick = {
                                if (uiState.appStartDestination != NavRoutes.Notes.route) {
                                    viewModel.setStartDestination(NavRoutes.Notes.route)
                                }
                            },
                            content = { Text(text = stringResource(id = R.string.notes_title)) }
                        )
                        FilterChip(
                            selected = uiState.appStartDestination == NavRoutes.Tasks.route,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_task_24),
                                    contentDescription = ""
                                )
                            },
                            onClick = {
                                if (uiState.appStartDestination != NavRoutes.Tasks.route) {
                                    viewModel.setStartDestination(NavRoutes.Tasks.route)
                                }
                            },
                            content = { Text(text = stringResource(id = R.string.tasks_title)) }
                        )
                    }
                }
            }
        }

    }
}