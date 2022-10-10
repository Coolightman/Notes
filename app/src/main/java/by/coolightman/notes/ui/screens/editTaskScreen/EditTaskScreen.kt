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
import by.coolightman.notes.ui.theme.Gold
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
    var text by remember {
        mutableStateOf("")
    }
    var createdAt by remember {
        mutableStateOf("")
    }
    var editedAt by remember {
        mutableStateOf("")
    }
    var selectedColor by remember {
        mutableStateOf(0)
    }
    var isImportant by remember {
        mutableStateOf(false)
    }
    var numberOfLines by remember {
        mutableStateOf(1)
    }
    LaunchedEffect(uiState) {
        text = uiState.text
        selectedColor = uiState.colorIndex
        isImportant = uiState.isImportant
        createdAt = uiState.createdAt
        editedAt = uiState.editedAt
        if (createdAt.isEmpty()) {
            selectedColor = uiState.newTaskColorPrefIndex
        }
    }
    val scrollState = rememberScrollState()
    val itemColors = remember { ItemColor.values() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        AppTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        goBack(scope, focusManager, navController)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                    )
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
                    .background(Color(itemColors[selectedColor].color).copy(0.5f))
            ) {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_outline_circle_24
                        ),
                        contentDescription = "active task",
                        tint = if (isImportant) Gold
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
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        if (editedAt.isNotEmpty()) {
            DateText(
                text = stringResource(R.string.edited) + " " + editedAt,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        SelectColorBar(
            selected = selectedColor,
            onSelect = { selectedColor = it },
            modifier = Modifier.padding(8.dp)
        )
        SwitchCard(
            label = stringResource(R.string.important_task),
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