package by.coolightman.notes.ui.screens.editNoteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.coolightman.notes.ui.components.CustomTextField
import by.coolightman.notes.ui.components.DateText
import by.coolightman.notes.ui.components.NoteTitleField
import by.coolightman.notes.ui.components.SelectColorBar
import by.coolightman.notes.ui.model.ItemColors
import by.coolightman.notes.util.toFormattedDate

@Composable
fun EditNoteScreen(
    navController: NavController,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val state = viewModel.uiState

    var title by rememberSaveable {
        mutableStateOf(state.title)
    }

    var text by rememberSaveable {
        mutableStateOf(state.text)
    }

    val dateText by rememberSaveable {
        mutableStateOf(System.currentTimeMillis().toFormattedDate())
    }

    var selectedColor by rememberSaveable {
        mutableStateOf(0)
    }

    val scrollState = rememberScrollState()

    val itemColors = remember { ItemColors.values() }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(itemColors[selectedColor].color))
            ) {
                NoteTitleField(
                    title = title,
                    onValueChange = { title = it },
                    focusManager = focusManager
                )
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 48.dp)
                        ) {
                            CustomTextField(
                                text = text,
                                onValueChange = {
                                    text = it
                                },
                                focusManager = focusManager,
                                onDoneKeyboardAction = {
                                    saveNote(text, viewModel, title, selectedColor, navController)
                                }
                            )
                        }
                        DateText(text = dateText)
                    }
                }
            }
        }
        SelectColorBar(
            selected = selectedColor,
            onSelect = { selectedColor = it },
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    saveNote(text, viewModel, title, selectedColor, navController)
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "save note",
                    modifier = Modifier.size(36.dp)
                )
            }
        }

    }
}

private fun saveNote(
    text: String,
    viewModel: EditNoteViewModel,
    title: String,
    selectedColor: Int,
    navController: NavController
) {
    if (text.isNotEmpty()) {
        viewModel.saveNote(title, text, selectedColor)
        navController.popBackStack()
    }
}