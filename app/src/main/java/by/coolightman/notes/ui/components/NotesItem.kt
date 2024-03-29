package by.coolightman.notes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.theme.EmptyBackground
import by.coolightman.notes.util.toFormattedFullDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesItem(
    note: Note,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    onCheckedChange: () -> Unit,
    isSelectionMode: Boolean = false,
    isShowNoteDate: Boolean = true,
    isColoredBackground: Boolean = true,
    onCollapseClick: () -> Unit
) {
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    val rotateState by animateFloatAsState(
        targetValue = if (note.isCollapsed) 0f else 180f,
        animationSpec = tween(500)
    )
    val elevationState by animateDpAsState(
        targetValue = if (note.isSelected) 8.dp else 2.dp
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = elevationState,
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
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.h6.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp, horizontal = 8.dp)
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isColoredBackground) {
                                    Color(ItemColor.values()[note.colorIndex].color).copy(0.2f)
                                } else {
                                    EmptyBackground.copy(0.2f)
                                }
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(
                                    minHeight = if (isShowNoteDate || note.isCollapsable) 54.dp
                                    else 62.dp
                                )
                                .padding(
                                    12.dp, 8.dp, 12.dp,
                                    bottom = if (isShowNoteDate || note.isCollapsable) 0.dp
                                    else 8.dp
                                )
                                .animateContentSize()
                        ) {
                            Text(
                                text = note.text,
                                style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                                maxLines = if (note.isCollapsed) 2 else Integer.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopStart)
                            )
                        }

                        if (isShowNoteDate || note.isCollapsable) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                if (note.isCollapsable) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { onCollapseClick() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "drop down",
                                            tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                            modifier = Modifier
                                                .rotate(rotateState)
                                                .align(Alignment.Center)
                                        )
                                    }
                                }
                                if (isShowNoteDate) {
                                    val dateText = if (note.isEdited) {
                                        val edited = stringResource(R.string.edit)
                                        "$edited " + note.editedAt.toFormattedFullDate()
                                    } else note.createdAt.toFormattedFullDate()
                                    DateText(
                                        text = dateText,
                                        modifier =
                                        if (note.isCollapsable) Modifier.align(Alignment.Bottom)
                                        else Modifier
                                            .weight(1f)
                                            .align(Alignment.Bottom)
                                    )
                                }
                            }
                        }
                    }

                    if (note.isPinned) {
                        Icon(
                            painter = painterResource(R.drawable.ic_pin_24),
                            contentDescription = "pin",
                            tint = MaterialTheme.colors.onSurface.copy(0.5f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(2.dp)
                                .size(12.dp)
                        )
                    }
                }
            }
            if (isSelectionMode) {
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .width(itemWidth)
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
        isCollapsable = false,
        isCollapsed = false,
        isPinned = true,
        folderId = 0L
    )
    NotesItem(
        note = note,
        onClick = {},
        onLongPress = {},
        onCheckedChange = {},
        onCollapseClick = {}
    )
}
