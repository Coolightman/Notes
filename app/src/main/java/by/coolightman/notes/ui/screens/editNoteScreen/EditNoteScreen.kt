package by.coolightman.notes.ui.screens.editNoteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
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
import by.coolightman.notes.ui.theme.EmptyBackground
import by.coolightman.notes.ui.theme.ImportantAction
import by.coolightman.notes.util.showSnack
import by.coolightman.notes.util.toFormattedFullDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditNoteViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scrollState = rememberScrollState()
    val itemColors = remember { ItemColor.values() }
    val scope = rememberCoroutineScope()

    var title by remember(uiState.title) {
        mutableStateOf(uiState.title)
    }
    var text by remember(uiState.text) {
        mutableStateOf(uiState.text)
    }
    val dateText by remember {
        mutableStateOf(System.currentTimeMillis().toFormattedFullDate())
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
    var numberOfLines by remember {
        mutableStateOf(1)
    }
    var isAllowedToCollapse by remember(uiState.isAllowToCollapse) {
        mutableStateOf(uiState.isAllowToCollapse)
    }
    var isPinned by remember(uiState.isPinned) {
        mutableStateOf(uiState.isPinned)
    }

    var openDeleteDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteDialog) {
        AppAlertDialog(
            text = stringResource(R.string.sent_note_to_trash_dialog),
            confirmButtonText = stringResource(R.string.send),
            confirmButtonColor = ImportantAction,
            onConfirm = {
                openDeleteDialog = false
                viewModel.sentNoteToTrash()
                goBack(scope, focusManager, navController)
            },
            onCancel = { openDeleteDialog = false }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            navigationIcon = {
                IconButton(onClick = { goBack(scope, focusManager, navController) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSecondary
                    )
                }
            },
            actions = {
                if (createdAt.isNotEmpty()) {
                    IconButton(onClick = { openDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 0.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(itemColors[selectedColor].color).copy(0.8f))
                    ) {
                        NoteTitleField(
                            title = title,
                            onValueChange = { title = it },
                            focusManager = focusManager
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(0.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = if (uiState.isColoredBackground) {
                                                Color(itemColors[selectedColor].color).copy(0.2f)
                                            } else EmptyBackground.copy(0.2f)
                                        )
                                ) {
                                    CustomTextField(
                                        text = text,
                                        placeholder = stringResource(R.string.text_placeholder),
                                        onValueChange = { text = it },
                                        onTextLayout = { textLayoutResult ->
                                            numberOfLines =
                                                derivedStateOf { textLayoutResult.lineCount }.value
                                        },
                                        keyboardController = keyboardController,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .defaultMinSize(minHeight = 54.dp)
                                            .padding(12.dp, 8.dp, 12.dp, 0.dp)
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        if (isAllowedToCollapse) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowDropDown,
                                                contentDescription = "drop down",
                                                tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                                modifier = Modifier
                                                    .rotate(180f)
                                                    .weight(1f)
                                            )
                                        }

                                        DateText(
                                            text = dateText,
                                            modifier = if (isAllowedToCollapse) Modifier.align(
                                                Alignment.Bottom
                                            )
                                            else Modifier.weight(1f)
                                        )
                                    }
                                }
                                if (isPinned) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_pin_24),
                                        contentDescription = "pin",
                                        tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(2.dp)
                                            .size(12.dp)
                                    )
                                }
                            }
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
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
                )

                SwitchCard(
                    label = stringResource(R.string.pin_at_top),
                    checked = isPinned,
                    onCheckedChange = { isPinned = !isPinned }
                )

                if (numberOfLines > 2) {
                    SwitchCard(
                        label = stringResource(R.string.allow_to_collapse),
                        checked = isAllowedToCollapse,
                        onCheckedChange = { isAllowedToCollapse = !isAllowedToCollapse }
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }

            DoneButton(modifier = Modifier.align(Alignment.BottomEnd)) {
                if (text.trim().isNotEmpty()) {
                    viewModel.saveNote(
                        title.trim(), text.trim(), selectedColor,
                        isCollapsable =
                        if (numberOfLines > 2) isAllowedToCollapse
                        else false,
                        isPinned = isPinned
                    )
                    goBack(scope, focusManager, navController)
                } else {
                    showSnack(scope, scaffoldState, context.getString(R.string.empty_note))
                }
            }
        }
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
