package by.coolightman.notes.ui.screens.notesScreen

import android.view.HapticFeedbackConstants
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.*
import by.coolightman.notes.ui.model.NavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: NavController, viewModel: NotesViewModel = hiltViewModel(),
    isVisibleFAB: (Boolean) -> Unit
) {
    val uiState = viewModel.uiState
    val density = LocalDensity.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isShowSortPanel by rememberSaveable {
        mutableStateOf(false)
    }
    var sortByCash by rememberSaveable {
        mutableStateOf(-1)
    }

//  scroll list to top only when another sortBy
    LaunchedEffect(uiState.sortByIndex) {
        if (sortByCash != uiState.sortByIndex) {
            sortByCash = uiState.sortByIndex
            delay(500)
            listState.animateScrollToItem(0)
        }
    }
    var isDropMenuExpanded by remember {
        mutableStateOf(false)
    }
    val fabVisibility = listState.isScrollingUp()
    LaunchedEffect(fabVisibility) {
        isVisibleFAB(fabVisibility)
    }
    var isSelectionMode by remember {
        mutableStateOf(false)
    }
    val view = LocalView.current
    LaunchedEffect(isSelectionMode) {
        if (isSelectionMode) {
            view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        }
    }
    if (isSelectionMode) {
        BackHandler {
            isSelectionMode = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopAppBar(
            title = {
                AppTitleText(text = stringResource(id = R.string.notes_title))
            },
            actions = {
                if (uiState.list.isNotEmpty()) {
                    IconButton(onClick = { isShowSortPanel = !isShowSortPanel }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                            contentDescription = "sort",
                            tint = if (isShowSortPanel) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                            }
                        )
                    }
                }
                IconButton(onClick = { isDropMenuExpanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "more",
                        tint = if (isDropMenuExpanded) {
                            MaterialTheme.colors.primary
                        } else {
                            MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
                        }
                    )
                }
                DropdownMenu(
                    expanded = isDropMenuExpanded,
                    onDismissRequest = { isDropMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            navController.navigate(NavRoutes.NotesTrash.route) {
                                launchSingleTop = true
                            }
                            isDropMenuExpanded = false
                        }
                    ) {
                        BadgedIcon(
                            icon = painterResource(id = R.drawable.ic_delete_full_24),
                            iconEmptyBadge = painterResource(id = R.drawable.ic_delete_empty_24),
                            badgeValue = uiState.trashCount
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.trash))
                    }
                    DropdownMenuItem(onClick = {
                        navController.navigate(NavRoutes.Settings.route) {
                            launchSingleTop = true
                        }
                        isDropMenuExpanded = false
                    }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = stringResource(R.string.settings))
                    }
                    if (uiState.notesCount != 0) {
                        Divider()
                        CountRow(
                            label = stringResource(R.string.total_count),
                            value = uiState.notesCount
                        )
                    }
                }
            })

        SortPanel(
            isVisible = isShowSortPanel,
            onSort = {
                viewModel.setSortBy(it)
            },
            currentSortIndex = uiState.sortByIndex
        )

        if (uiState.list.isEmpty()) {
            EmptyContentSplash(
                iconId = R.drawable.ic_note_24, textId = R.string.no_notes
            )
        } else {

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = uiState.list, key = { it.id }) { note ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        var itemHeight by remember {
                            mutableStateOf(0.dp)
                        }
                        var itemWidth by remember {
                            mutableStateOf(0.dp)
                        }
                        NotesItem(
                            item = note,
                            modifier = Modifier.onGloballyPositioned { coordinates ->
                                itemHeight = density.run { coordinates.size.height.toDp() }
                                itemWidth = density.run { coordinates.size.width.toDp() }
                            },
                            onClick = {
                                navController.navigate(NavRoutes.EditNote.withArgs(note.id.toString())) {
                                    launchSingleTop = true
                                }
                            },
                            onLongPress = {
                                scope.launch {
                                    viewModel.resetSelections(note.id)
                                    delay(50)
                                    isSelectionMode = true
                                }
                            }
                        )
                        if (isSelectionMode) {
                            Box(
                                modifier = Modifier
                                    .height(itemHeight)
                                    .width(itemWidth)
                                    .background(
                                        MaterialTheme.colors.secondary.copy(
                                            alpha = if (note.isSelected) 0.4f
                                            else 0.2f
                                        )
                                    )
                                    .align(Alignment.Center)
                                    .clickable { viewModel.setIsSelectedNote(note.id) }
                            ) {
                                Checkbox(
                                    checked = note.isSelected,
                                    onCheckedChange = { viewModel.setIsSelectedNote(note.id) },
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}