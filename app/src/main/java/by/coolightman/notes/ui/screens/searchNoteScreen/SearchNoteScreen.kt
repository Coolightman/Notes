package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import by.coolightman.notes.R
import by.coolightman.notes.ui.components.AppTopAppBar
import by.coolightman.notes.ui.components.CustomTextField
import by.coolightman.notes.ui.components.EmptyContentSplash
import by.coolightman.notes.ui.components.NotesItem
import by.coolightman.notes.ui.model.NavRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchNoteScreen(
    navController: NavHostController,
    viewModel: SearchNoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var searchKey by rememberSaveable {
        mutableStateOf("")
    }

    var isShowCancelBt by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(searchKey) {
        isShowCancelBt = searchKey.isNotEmpty()
        if (searchKey.length >= 2) {
            viewModel.setSearchKey(searchKey)
        } else {
            viewModel.clearSearchResult()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
            title = {
                CustomTextField(
                    text = searchKey,
                    onValueChange = { searchKey = it },
                    placeholder = stringResource(R.string.search_placeholder),
                    onTextLayout = {},
                    singleLine = true,
                    keyboardController = keyboardController,
                    textColor = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                )
            },
            actions = {
                if (isShowCancelBt) {
                    IconButton(onClick = { searchKey = "" }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                            contentDescription = "cancel",
                            tint = MaterialTheme.colors.onSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        )

        if (uiState.list.isEmpty() && searchKey.length > 1) {
            EmptyContentSplash(
                iconId = R.drawable.ic_search_splash_24,
                textId = R.string.no_results
            )
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = uiState.list, key = { it.id }) { note ->
                    NotesItem(
                        note = note,
                        onClick = {
                            navController.navigate(
                                NavRoutes.EditNote.withArgs(note.id.toString())
                            ) {
                                launchSingleTop = true
                            }
                        },
                        onLongPress = { },
                        onCheckedChange = { },
                        modifier = Modifier.animateItemPlacement(),
                        onCollapseClick = { viewModel.switchCollapse(note.id) }
                    )
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
