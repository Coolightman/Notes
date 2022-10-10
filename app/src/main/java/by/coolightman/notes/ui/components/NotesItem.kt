package by.coolightman.notes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.theme.InactiveBackground
import by.coolightman.notes.util.toFormattedDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesItem(
    note: Note,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    onCheckedChange: () -> Unit,
    isSelectionMode: Boolean = false,
    isShowNoteDate: Boolean = true
) {
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = elevation,
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                itemHeight = density.run { coordinates.size.height.toDp() }
                itemWidth = density.run { coordinates.size.width.toDp() }
            }
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = { onClick() },
                        onLongClick = { onLongPress() }
                    )
            ) {
                if (note.title.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(ItemColor.values()[note.colorIndex].color).copy(0.8f))
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = note.title,
                            style = MaterialTheme.typography.h6.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(ItemColor.values()[note.colorIndex].color).copy(0.8f))
                            .height(4.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(ItemColor.values()[note.colorIndex].color).copy(0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
                    ) {
                        Text(
                            text = note.text,
                            style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(12.dp, 8.dp, 12.dp, 0.dp)
                        )
                    }

                    if (isShowNoteDate) {
                        val dateText = if (note.isEdited) {
                            val edited = stringResource(R.string.edit)
                            "$edited " + note.editedAt.toFormattedDate()
                        } else note.createdAt.toFormattedDate()
                        DateText(text = dateText)
                    }
                }
            }
            if (isSelectionMode) {
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .width(itemWidth)
                        .background(
                            InactiveBackground.copy(
                                alpha = if (note.isSelected) 0.4f
                                else 0f
                            )
                        )
                        .align(Alignment.Center)
                        .clickable { onCheckedChange() }
                ) {
                    AppCheckbox(
                        checked = note.isSelected,
                        onCheckedChange = { onCheckedChange() },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun NotesItemPreview() {
    val note = Note(
        title = "Title",
        text = "Text text text Text text text \nText text text",
        colorIndex = 1,
        createdAt = 19871988977L,
        editedAt = 198719889775L,
        isShowDate = true,
        isEdited = false,
        isInTrash = false,
        isSelected = false,
        isExpandable = false,
        isExpanded = false
    )
    NotesItem(note = note, onClick = {}, onLongPress = {}, onCheckedChange = {})
}