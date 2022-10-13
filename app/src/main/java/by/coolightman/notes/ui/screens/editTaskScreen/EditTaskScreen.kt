package by.coolightman.notes.ui.screens.editTaskScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.theme.ImportantTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTaskScreen(
    navController: NavController,
    viewModel: EditTaskViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scrollState = rememberScrollState()
    val itemColors = remember { ItemColor.values() }
    val scope = rememberCoroutineScope()

    var text by remember(uiState.text) {
        mutableStateOf(uiState.text)
    }
    val createdAt by remember(uiState.createdAt) {
        mutableStateOf(uiState.createdAt)
    }
    val editedAt by remember(uiState.editedAt) {
        mutableStateOf(uiState.editedAt)
    }
    var selectedColor by remember(uiState.colorIndex) {
        mutableStateOf(uiState.colorIndex)
    }
    var isImportant by remember(uiState.isImportant) {
        mutableStateOf(uiState.isImportant)
    }
    var numberOfLines by remember {
        mutableStateOf(1)
    }
    var openDeleteDialog by remember {
        mutableStateOf(false)
    }

    when {
        openDeleteDialog -> {
            AppAlertDialog(text = stringResource(R.string.delete_task_dialog),
                secondaryText = stringResource(id = R.string.can_not_restore_it),
                confirmButtonText = stringResource(R.string.delete),
                confirmButtonColor = MaterialTheme.colors.error,
                onConfirm = {
                    openDeleteDialog = false
                    viewModel.deleteTask()
                    goBack(scope, focusManager, navController)
                },
                onCancel = { openDeleteDialog = false })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        AppTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { goBack(scope, focusManager, navController) }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                    )
                }
            },
            actions = {
                if (createdAt.isNotEmpty()) {
                    IconButton(onClick = { openDeleteDialog = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete_forever_24),
                            contentDescription = "delete",
                            tint = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                        )
                    }
                }
            }
        )

        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .padding(12.dp, 12.dp, 12.dp, 0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(itemColors[selectedColor].color).copy(0.3f))
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_circle_24),
                        contentDescription = "active task",
                        tint = if (isImportant) ImportantTask
                        else LocalContentColor.current
                    )
                }

                CustomTextField(
                    text = text,
                    placeholder = stringResource(R.string.text_placeholder),
                    onValueChange = { text = it },
                    keyboardController = keyboardController,
                    onTextLayout = { textLayoutResult ->
                        numberOfLines = derivedStateOf { textLayoutResult.lineCount }.value
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp)
                )

                if (numberOfLines > 1) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.align(Alignment.Bottom)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "drop down",
                            modifier = Modifier.rotate(180f)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }

        if (createdAt.isNotEmpty()) {
            DateText(
                text = stringResource(R.string.created) + " " + createdAt,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            )
        }

        if (editedAt.isNotEmpty()) {
            DateText(
                text = stringResource(R.string.edited) + " " + editedAt,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            )
        }

        SelectColorBar(
            selected = selectedColor,
            onSelect = { selectedColor = it },
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            alpha = 0.4f
        )

        SwitchCard(label = stringResource(R.string.important_task),
            checked = isImportant,
            onCheckedChange = { isImportant = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            DoneButton {
                if (text.trim().isNotEmpty()) {
                    viewModel.saveTask(text.trim(), selectedColor, isImportant, numberOfLines)
                    goBack(scope, focusManager, navController)
                } else {
                    showSnack(scope, scaffoldState, context.getString(R.string.empty_task))
                }
            }
        }
    }
}

private fun showSnack(
    scope: CoroutineScope, scaffoldState: ScaffoldState, text: String
) {
    scope.launch {
        val job = launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = text,
                duration = SnackbarDuration.Indefinite
            )
        }
        delay(2000L)
        job.cancel()
    }
}

private fun goBack(
    scope: CoroutineScope, focusManager: FocusManager, navController: NavController
) {
    scope.launch {
        focusManager.clearFocus()
        delay(100)
        navController.popBackStack()
    }
}