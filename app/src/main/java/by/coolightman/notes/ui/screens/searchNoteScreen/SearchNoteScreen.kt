package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var searchKey by remember {
        mutableStateOf("")
    }

    LaunchedEffect(searchKey) {
        if (searchKey.length >= 2) {
            viewModel.searchByKey(searchKey)
        } else {
            viewModel.clearSearchResult()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
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
            },
            title = {
                CustomTextField(
                    text = searchKey,
                    onValueChange = { searchKey = it },
                    placeholder = stringResource(R.string.search_placeholder),
                    onTextLayout = {},
                    singleLine = true,
                    keyboardController = keyboardController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)

                )
            }

        )
        if (viewModel.uiState.list.isEmpty() && searchKey.length > 1) {
            EmptyContentSplash(
                iconId = R.drawable.ic_search_splash_24,
                textId = R.string.no_results
            )
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = viewModel.uiState.list, key = { it.id }) { note ->
                    NotesItem(
                        note = note,
                        onClick = {
                            navController.navigate(NavRoutes.EditNote.withArgs(note.id.toString())) {
                                launchSingleTop = true
                            }
                        },
                        onLongPress = { },
                        onCheckedChange = {  },
                        modifier = Modifier.animateItemPlacement(),
                        onExpandClick = {  }
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