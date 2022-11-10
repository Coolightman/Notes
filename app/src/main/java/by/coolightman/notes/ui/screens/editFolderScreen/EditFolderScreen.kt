package by.coolightman.notes.ui.screens.editFolderScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import by.coolightman.notes.ui.theme.ImportantAction
import by.coolightman.notes.ui.theme.YellowFolder
import by.coolightman.notes.util.showSnack
import by.coolightman.notes.util.toFormattedFullDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditFolderScreen(
    navController: NavController,
    viewModel: EditFolderViewModel = hiltViewModel(),
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
    val dateText by remember {
        mutableStateOf(System.currentTimeMillis().toFormattedFullDate())
    }
    val createdAt by remember(uiState.createdAt) {
        mutableStateOf(uiState.createdAt)
    }
    var selectedColor by remember(uiState.colorIndex) {
        mutableStateOf(uiState.colorIndex)
    }
    var isPinned by remember(uiState.isPinned) {
        mutableStateOf(uiState.isPinned)
    }

    var openDeleteDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteDialog) {
        AppAlertDialog(
            text = stringResource(R.string.sent_folder_to_trash_dialog),
            confirmButtonText = stringResource(R.string.send),
            confirmButtonColor = ImportantAction,
            onConfirm = {
                openDeleteDialog = false
                viewModel.sentFolderToTrash()
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
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(itemColors[selectedColor].color).copy(0.2f))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_folder_24),
                                contentDescription = "folder",
                                tint = getTint(itemColors, selectedColor),
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .size(32.dp)
                            )
                            CustomTextField(
                                text = title,
                                onValueChange = { title = it },
                                keyboardController = keyboardController,
                                placeholder = stringResource(id = R.string.title_placeholder),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                            )
                        }
                        if (isPinned){
                            Icon(
                                painter = painterResource(R.drawable.ic_pin_24),
                                contentDescription = "pin",
                                tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(12.dp)
                            )
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
                } else {
                    DateText(
                        text = dateText,
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

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }

            DoneButton(modifier = Modifier.align(Alignment.BottomEnd)) {
                if (title.trim().isNotEmpty()) {
                    viewModel.saveFolder(
                        title = title.trim(),
                        colorIndex = selectedColor,
                        isPinned = isPinned
                    )
                    goBack(scope, focusManager, navController)
                } else {
                    showSnack(scope, scaffoldState, context.getString(R.string.empty_title))
                }
            }
        }
    }
}

@Composable
private fun getTint(
    itemColors: Array<ItemColor>,
    selectedColor: Int
): Color {
    return if (selectedColor!=1){
        Color(itemColors[selectedColor].color).copy(0.8f)
    } else{
        YellowFolder
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
