package by.coolightman.notes.ui.screens.settingsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
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
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.NotesViewMode
import by.coolightman.notes.ui.model.ThemeMode

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = { AppTitleText(text = stringResource(R.string.settings_title)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SettingsRow(title = stringResource(R.string.start_screen)) {
                StartDestinationChip(
                    icon = painterResource(R.drawable.ic_note_24),
                    appStartDestination = uiState.appStartDestination,
                    chipDestination = NavRoutes.Notes.route,
                    title = stringResource(R.string.notes_title),
                    onClick = { viewModel.setStartDestination(it) }
                )

                StartDestinationChip(
                    icon = painterResource(R.drawable.ic_task_24),
                    appStartDestination = uiState.appStartDestination,
                    chipDestination = NavRoutes.Tasks.route,
                    title = stringResource(R.string.tasks_title),
                    onClick = { viewModel.setStartDestination(it) }
                )
            }

            SettingsRow(title = stringResource(R.string.theme)) {
                ThemeModeChip(
                    icon = painterResource(R.drawable.ic_theme_system_24),
                    appThemeMode = uiState.themeMode,
                    chipThemeMode = ThemeMode.SYSTEM_MODE,
                    title = stringResource(R.string.system_theme),
                    onClick = { viewModel.setThemeMode(it) }
                )

                ThemeModeChip(
                    icon = painterResource(R.drawable.ic_theme_night_24),
                    appThemeMode = uiState.themeMode,
                    chipThemeMode = ThemeMode.DARK_MODE,
                    title = stringResource(R.string.dark_theme),
                    onClick = { viewModel.setThemeMode(it) }
                )

                ThemeModeChip(
                    icon = painterResource(R.drawable.ic_theme_day_24),
                    appThemeMode = uiState.themeMode,
                    chipThemeMode = ThemeMode.LIGHT_MODE,
                    title = stringResource(R.string.light_theme),
                    onClick = { viewModel.setThemeMode(it) }
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SettingsRow(title = stringResource(R.string.new_note_color)) {
                SelectColorBar(
                    selected = uiState.newNoteColorIndex,
                    onSelect = { viewModel.setNewNoteColor(it) }
                )
            }

            SettingsRow(title = "Notes view mode") {
                NotesViewModeChip(
                    icon = painterResource(R.drawable.ic_list_mode_24),
                    currentViewMode = uiState.currentNotesViewMode,
                    chipViewMode = NotesViewMode.LIST,
                    title = stringResource(R.string.list_mode),
                    onClick = { viewModel.setNotesViewMode(it) }
                )

                NotesViewModeChip(
                    icon = painterResource(R.drawable.ic_grid_mode_24),
                    currentViewMode = uiState.currentNotesViewMode,
                    chipViewMode = NotesViewMode.GRID,
                    title = stringResource(R.string.grid_mode),
                    onClick = { viewModel.setNotesViewMode(it) }
                )
            }

            SwitchCard(
                label = stringResource(R.string.show_notes_date),
                checked = uiState.isShowNotesDate,
                onCheckedChange = { viewModel.setIsShowNotedDate(it) }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SettingsRow(title = stringResource(R.string.new_task_color)) {
                SelectColorBar(
                    selected = uiState.newTaskColorIndex,
                    onSelect = { viewModel.setNewTaskColor(it) }
                )
            }
        }
    }
}