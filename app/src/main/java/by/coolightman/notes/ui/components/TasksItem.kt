package by.coolightman.notes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.ui.model.ItemColors
import by.coolightman.notes.ui.theme.Gold

@Composable
fun TasksItem(
    item: Task,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    onClick: () -> Unit,
    onSwitchActive: () -> Unit
) {

    val backgroundAlfa = if (item.isActive) 0.5f
    else 0.2f

    val contentAlfa = if (item.isActive) 1f
    else 0.5f

    val textStyle = if (item.isActive) {
        MaterialTheme.typography.body1.copy(
            fontSize = 18.sp
        )
    } else {
        MaterialTheme.typography.body1.copy(
            fontSize = 18.1.sp, textDecoration = TextDecoration.LineThrough
        )
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = elevation,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .background(Color(ItemColors.values()[item.colorIndex].color).copy(backgroundAlfa)))
        {
            IconButton(onClick = { onSwitchActive() }) {
                Icon(
                    painter = painterResource(
                        id = if (item.isActive) R.drawable.ic_outline_circle_24
                        else R.drawable.ic_task_24
                    ),
                    contentDescription = "active task",
                    tint = if (item.isImportant) Gold.copy(contentAlfa)
                    else LocalContentColor.current.copy(contentAlfa)
                )
            }
            Text(
                text = item.text,
                style = textStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 4.dp, 8.dp, 4.dp)
                    .alpha(contentAlfa)
            )
        }
    }
}

@Preview(
    showBackground = true, uiMode = UI_MODE_NIGHT_YES
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
        isHidden = false
    )
    TasksItem(item = task, onClick = {}, onSwitchActive = {})
}