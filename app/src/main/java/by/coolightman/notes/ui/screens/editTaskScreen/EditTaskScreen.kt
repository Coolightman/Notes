package by.coolightman.notes.ui.screens.editTaskScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import by.coolightman.notes.ui.components.AppAlertDialog
import by.coolightman.notes.ui.components.AppTopAppBar
import by.coolightman.notes.ui.components.CustomTextField
import by.coolightman.notes.ui.components.DatePicker
import by.coolightman.notes.ui.components.DateText
import by.coolightman.notes.ui.components.DoneButton
import by.coolightman.notes.ui.components.NotificationAddCard
import by.coolightman.notes.ui.components.NotificationDateTimeText
import by.coolightman.notes.ui.components.SelectColorBar
import by.coolightman.notes.ui.components.SwitchCard
import by.coolightman.notes.ui.components.TaskNotificationDate
import by.coolightman.notes.ui.components.TimePicker
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.theme.ImportantAction
import by.coolightman.notes.ui.theme.ImportantTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun EditTaskScreen(
    navController: NavController,
    viewModel: EditTaskViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scrollState = rememberScrollState()
    val itemColors = remember { ItemColor.values() }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

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
    val isHasNotification by remember(uiState.isHasNotification) {
        mutableStateOf(uiState.isHasNotification)
    }
    var numberOfLines by remember {
        mutableStateOf(1)
    }

    var openDeleteDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteDialog) {
        AppAlertDialog(
            text = stringResource(R.string.delete_task_dialog),
            confirmButtonText = stringResource(R.string.delete),
            confirmButtonColor = ImportantAction,
            onConfirm = {
                openDeleteDialog = false
                viewModel.deleteTask()
                goBack(scope, focusManager, navController)
            },
            onCancel = { openDeleteDialog = false }
        )
    }

    var calendar by remember {
        mutableStateOf(Calendar.getInstance(Locale.getDefault()))
    }
    var openTimePicker by remember {
        mutableStateOf(false)
    }
    if (openTimePicker) {
        TimePicker(
            calendar = calendar,
            onCancel = { openTimePicker = false },
            selectedTime = { calendar = it }
        )
    }

    var openDatePicker by remember {
        mutableStateOf(false)
    }
    if (openDatePicker) {
        DatePicker(
            calendar = calendar,
            onCancel = { openDatePicker = false },
            selectedTime = { calendar = it }
        )
    }

    BackHandler(
        enabled = bottomSheetState.isVisible
    ) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(12.dp)
            ) {
                NotificationDateTimeText(
                    notificationDate = calendar.timeInMillis,
                    onClickTime = { openTimePicker = true },
                    onClickDate = { openDatePicker = true }
                )
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppTopAppBar(navigationIcon = {
                IconButton(onClick = { goBack(scope, focusManager, navController) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSecondary
                    )
                }
            }, actions = {
                if (createdAt.isNotEmpty()) {
                    IconButton(onClick = { openDeleteDialog = true }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete_forever_24),
                            contentDescription = "delete",
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            })

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
                            .padding(12.dp, 12.dp, 12.dp, 0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(itemColors[selectedColor].color).copy(0.4f))
                        ) {
                            Box {
                                IconButton(onClick = { }) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_outline_circle_24),
                                        contentDescription = "active task",
                                        tint = if (isImportant) ImportantTask
                                        else MaterialTheme.colors.onSurface.copy(0.8f),
                                    )
                                }
                                if (isHasNotification && !uiState.isShowNotificationDate) {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = "notifications",
                                        tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(4.dp)
                                            .size(12.dp)
                                    )
                                }
                            }

                            CustomTextField(
                                text = text,
                                placeholder = stringResource(R.string.text_placeholder),
                                onValueChange = { text = it },
                                keyboardController = keyboardController,
                                onTextLayout = { textLayoutResult ->
                                    numberOfLines =
                                        derivedStateOf { textLayoutResult.lineCount }.value
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 4.dp)
                            )

                            if (numberOfLines > 1) {
                                IconButton(
                                    onClick = { }, modifier = Modifier.align(Alignment.Bottom)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "drop down",
                                        tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                        modifier = Modifier.rotate(180f)
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.width(48.dp))
                            }
                        }
                    }
                    TaskNotificationDate(
                        isHasNotification = isHasNotification && uiState.isShowNotificationDate,
                        notificationTime = calendar.timeInMillis,
                        modifier = Modifier.padding(end = 12.dp)
                    )

                    if (createdAt.isNotEmpty()) {
                        DateText(
                            text = stringResource(R.string.created) + " " + createdAt,
                            modifier = Modifier
                                .padding(horizontal = 28.dp)
                                .fillMaxWidth()
                        )
                    }

                    if (editedAt.isNotEmpty()) {
                        DateText(
                            text = stringResource(R.string.edited) + " " + editedAt,
                            modifier = Modifier
                                .padding(horizontal = 28.dp)
                                .fillMaxWidth()
                        )
                    }

                    SelectColorBar(
                        selected = selectedColor,
                        onSelect = { selectedColor = it },
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                        alpha = 0.5f
                    )

                    SwitchCard(
                        label = stringResource(R.string.important_task),
                        checked = isImportant,
                        onCheckedChange = { isImportant = it }
                    )

                    NotificationAddCard(
                        label = stringResource(R.string.add_notification),
                        onClickAdd = {
                            scope.launch {
                                focusManager.clearFocus()
                                bottomSheetState.show()
                            }
                        }
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    )
                }

                DoneButton(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    when {
                        text.trim().isEmpty() -> {
                            showSnack(scope, scaffoldState, context.getString(R.string.empty_task))
                        }
                        isHasNotification && !isValidDate(calendar) -> {
                            showSnack(
                                scope,
                                scaffoldState,
                                context.getString(R.string.wrong_notification_time)
                            )
                        }
                        else -> {
                            viewModel.saveTask(
                                text.trim(),
                                selectedColor,
                                isImportant,
                                numberOfLines,
                                isHasNotification,
                                calendar
                            )
                            goBack(scope, focusManager, navController)
                        }
                    }
                }
            }
        }
    }
}

fun isValidDate(calendar: Calendar): Boolean {
    val checkCalendar = Calendar.getInstance(Locale.getDefault())
    return checkCalendar.timeInMillis < calendar.timeInMillis
}

private fun showSnack(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    text: String
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
    scope: CoroutineScope,
    focusManager: FocusManager,
    navController: NavController
) {
    scope.launch {
        focusManager.clearFocus()
        delay(100)
        navController.popBackStack()
    }
}
