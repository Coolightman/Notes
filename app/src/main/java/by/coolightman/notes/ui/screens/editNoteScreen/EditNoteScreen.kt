package by.coolightman.notes.ui.screens.editNoteScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val itemColors = remember { ItemColors.values() }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            ) {
                NoteTitleField(
                    title = title,
                    onValueChange = { title = it },
                    backgroundColor = Color(itemColors[selectedColor].color)
                )
                Card(
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
    }
}