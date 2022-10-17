package by.coolightman.notes.ui.screens.notesTrashScreen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.util.DISMISS_DELAY
import by.coolightman.notes.util.FRACTIONAL_THRESHOLD
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesTrashScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: NotesTrashViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var openDeleteAllDialog by remember {
        mutableStateOf(false)
    }
    if (openDeleteAllDialog) {
        AppAlertDialog(
            text = stringResource(R.string.delete_all_notes_dialog),
            secondaryText = stringResource(R.string.can_not_restore_it),
            confirmButtonText = stringResource(R.string.delete),
            confirmButtonColor = MaterialTheme.colors.error,
            onConfirm = {
                viewModel.deleteAllTrash()
                openDeleteAllDialog = false
            },
            onCancel = { openDeleteAllDialog = false }
        )
    }

    var openRestoreAllDialog by remember {
        mutableStateOf(false)
    }
    if (openRestoreAllDialog) {
        AppAlertDialog(
            text = stringResource(R.string.restore_all_notes_dialog),
            confirmButtonText = stringResource(R.string.restore),
            onConfirm = {
                viewModel.restoreAllTrash()
                openRestoreAllDialog = false
            },
            onCancel = { openRestoreAllDialog = false }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = { AppTitleText(text = stringResource(R.string.trash_title)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                    )
                }
            },
            actions = {
                Spacer(modifier = Modifier.width(16.dp))

                IconButton(
                    onClick = {
                        if (uiState.list.isNotEmpty()) {
                            openRestoreAllDialog = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_restore_trash_24),
                        contentDescription = "restore all",
                        tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                    )
                }

                IconButton(
                    onClick = {
                        if (uiState.list.isNotEmpty()) {
                            openDeleteAllDialog = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_forever_24),
                        contentDescription = "delete all forever",
                        tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                    )
                }
            }
        )

        if (uiState.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_delete_empty_24,
                textId = R.string.no_trash
            )
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = uiState.list, key = { it.id }) { note ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            when (it) {
                                DismissValue.DismissedToEnd -> {
                                    scope.launch {
                                        delay(DISMISS_DELAY)
                                        viewModel.deleteNote(note.id)
                                        showSnackbar(scaffoldState, context, viewModel)
                                    }
                                    true
                                }
                                DismissValue.DismissedToStart -> {
                                    scope.launch {
                                        delay(DISMISS_DELAY)
                                        viewModel.restoreNote(note.id)
                                    }
                                    true
                                }
                                else -> true
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(
                            DismissDirection.StartToEnd,
                            DismissDirection.EndToStart
                        ),
                        dismissThresholds = { FractionalThreshold(FRACTIONAL_THRESHOLD) },
                        background = { DeleteRestoreSwipeSub(dismissState) },
                        modifier = Modifier.animateItemPlacement()
                    ) {
                        NotesItem(
                            note = note,
                            onClick = {},
                            onLongPress = {},
                            onCheckedChange = {},
                            onCollapseClick = {},
                            isCollapsed = note.isCollapsable
                        )
                    }
                }
            }
        }
    }
}

private suspend fun showSnackbar(
    scaffoldState: ScaffoldState,
    context: Context,
    viewModel: NotesTrashViewModel
) {
    val action = scaffoldState.snackbarHostState.showSnackbar(
        message = context.getString(R.string.note_deleted),
        actionLabel = context.getString(R.string.undo)
    )
    if (action == SnackbarResult.ActionPerformed) {
        viewModel.cancelDeletion()
    }
}