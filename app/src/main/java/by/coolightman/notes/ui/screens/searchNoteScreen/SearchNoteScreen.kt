package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchNoteScreen(
    navController: NavHostController,
    viewModel: SearchNoteViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    var searchKey by remember {
        mutableStateOf("")
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