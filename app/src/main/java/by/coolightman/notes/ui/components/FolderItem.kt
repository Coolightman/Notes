package by.coolightman.notes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Folder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderItem(
    folder: Folder,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    onCheckedChange: () -> Unit,
    isSelectionMode: Boolean = false,
) {

    val elevationState by animateDpAsState(
        targetValue = if (folder.isSelected) 8.dp else 2.dp
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = elevationState,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .combinedClickable(
                        onClick = { onClick() },
                        onLongClick = { onLongPress() }
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_folder_24),
                    contentDescription = "folder",
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = folder.title,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
            if (isSelectionMode) {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .clickable { onCheckedChange() }
                ) {
                    AppCheckbox(
                        checked = folder.isSelected,
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
    val folder = Folder(
        title = "First folder",
        createdAt = 0L,
        isInTrash = false,
        isSelected = false,
        isPinned = false
    )
    FolderItem(
        folder = folder,
        onClick = {},
        onLongPress = {},
        onCheckedChange = {}
    )
}
