package by.coolightman.notes.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.theme.ImportantTask
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksItem(
    task: Task,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onSwitchActive: () -> Unit,
    onLongPress: () -> Unit,
    onCheckedChange: () -> Unit,
    isSelectionMode: Boolean = false,
    isCollapsable: Boolean = false,
    isCollapsed: Boolean = false,
    isShowNotificationDate: Boolean = false,
    onCollapseClick: () -> Unit
) {

    val backgroundAlfa = if (task.isActive) 0.4f
    else 0.2f

    val contentAlfa = if (task.isActive) 1f
    else 0.5f

    val textStyle = if (task.isActive) {
        MaterialTheme.typography.body1.copy(
            fontSize = 18.sp
        )
    } else {
        MaterialTheme.typography.body1.copy(
            fontSize = 18.1.sp, textDecoration = TextDecoration.LineThrough
        )
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    val rotateState by animateFloatAsState(
        targetValue = if (isCollapsed) 0f else 180f,
        animationSpec = tween(500)
    )

    Column() {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation =
            if (task.isSelected) 8.dp
            else 2.dp,
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .onGloballyPositioned { coordinates ->
                    itemHeight = density.run { coordinates.size.height.toDp() }
                    itemWidth = density.run { coordinates.size.width.toDp() }
                }
        ) {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { onClick() },
                            onLongClick = { onLongPress() }
                        )
                        .background(
                            Color(ItemColor.values()[task.colorIndex].color).copy(backgroundAlfa)
                        )
                        .animateContentSize()
                ) {
                    Box {
                        IconButton(onClick = { onSwitchActive() }) {
                            Icon(
                                painter = painterResource(
                                    id = if (task.isActive) R.drawable.ic_outline_circle_24
                                    else R.drawable.ic_task_24
                                ),
                                contentDescription = "active task",
                                tint = if (task.isImportant) ImportantTask.copy(contentAlfa)
                                else MaterialTheme.colors.onSurface.copy(0.8f),
                            )
                        }
                        if (task.isHasNotification && !isShowNotificationDate) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "notifications",
                                tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .size(12.dp)
                            )
                        }
                    }
                    Text(
                        text = task.text,
                        style = textStyle,
                        maxLines =
                        if (isCollapsed) 1
                        else Integer.MAX_VALUE,

                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(0.dp, 4.dp, 8.dp, 4.dp)
                            .alpha(contentAlfa)
                    )
                    if (isCollapsable && !isSelectionMode) {
                        IconButton(
                            onClick = { onCollapseClick() },
                            modifier = Modifier.align(Alignment.Bottom)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "drop down",
                                tint = MaterialTheme.colors.onSurface.copy(0.5f),
                                modifier = Modifier.rotate(rotateState)
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.width(48.dp))
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
                            checked = task.isSelected,
                            onCheckedChange = { onCheckedChange() },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
        TaskNotificationDate(
            isHasNotification = task.isHasNotification && isShowNotificationDate,
            notificationTime = task.notificationTime.timeInMillis
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun NotesItemPreview() {
    val task = Task(
        text = "Text text text Text text text ",
        colorIndex = 1,
        createdAt = 19871988977L,
        editedAt = 198719889775L,
        isEdited = false,
        isImportant = true,
        isActive = false,
        isSelected = false,
        isCollapsable = false,
        isCollapsed = false,
        isHasNotification = true,
        notificationTime = Calendar.getInstance(Locale.getDefault())
    )
    TasksItem(
        task = task,
        onClick = {},
        onSwitchActive = {},
        onCheckedChange = {},
        onLongPress = {},
        onCollapseClick = {}
    )
}
