package by.coolightman.notes.ui.screens.notesTrashScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    viewModel: NotesTrashViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var openDeleteAllDialog by remember {
        mutableStateOf(false)
    }
    var openRestoreAllDialog by remember {
        mutableStateOf(false)
    }
    when {
        openDeleteAllDialog -> {
            AppAlertDialog(
                text = stringResource(R.string.delete_all_notes_dialog),
                confirmButtonText = stringResource(R.string.delete),
                onConfirm = {
                    viewModel.deleteAllTrash()
                    openDeleteAllDialog = false
                },
                onCancel = { openDeleteAllDialog = false }
            )
        }
        openRestoreAllDialog -> {
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
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        if (state.list.isNotEmpty()) {
                            openRestoreAllDialog = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_restore_trash_24),
                        contentDescription = "restore all"
                    )
                }
                IconButton(
                    onClick = {
                        if (state.list.isNotEmpty()) {
                            openDeleteAllDialog = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete_forever_24),
                        contentDescription = "delete all forever"
                    )
                }
            }
        )

        if (state.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_delete_empty_24,
                textId = R.string.no_trash
            )
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = state.list,
                    key = { it.id }
                ) { note ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            when (it) {
                                DismissValue.DismissedToEnd -> {
                                    scope.launch {
                                        delay(DISMISS_DELAY)
                                        viewModel.deleteNote(note.id)
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
                            item = note,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}