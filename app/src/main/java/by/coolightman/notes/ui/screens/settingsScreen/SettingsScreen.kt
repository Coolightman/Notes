package by.coolightman.notes.ui.screens.settingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.AppTitleText
import by.coolightman.notes.ui.components.AppTopAppBar
import by.coolightman.notes.ui.components.SelectColorBar
import by.coolightman.notes.ui.components.SettingsRow
import by.coolightman.notes.ui.components.StartDestinationChip
import by.coolightman.notes.ui.components.SwitchCard
import by.coolightman.notes.ui.components.ThemeModeChip
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = { AppTitleText(text = stringResource(R.string.settings_title)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSecondary
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

            SwitchCard(
                label = stringResource(R.string.show_notes_date),
                checked = uiState.isShowNotesDate,
                onCheckedChange = { viewModel.setIsShowNotedDate(it) }
            )

            SwitchCard(
                label = stringResource(R.string.colored_notes_background),
                checked = uiState.isNotesColoredBackground,
                onCheckedChange = { viewModel.setIsNotesColoredBackground(it) }
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SettingsRow(title = stringResource(R.string.new_task_color)) {
                SelectColorBar(
                    selected = uiState.newTaskColorIndex,
                    onSelect = { viewModel.setNewTaskColor(it) },
                    alpha = 0.6f
                )
            }

            SwitchCard(
                label = stringResource(R.string.show_notification_date),
                checked = uiState.isShowTaskNotificationDate,
                onCheckedChange = { viewModel.setIsShowTaskNotificationDate(it) }
            )
        }
    }
}
