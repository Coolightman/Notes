package by.coolightman.notes.ui.components

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.theme.YellowFolder

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
                    .background(Color(ItemColor.values()[folder.colorIndex].color).copy(0.2f))
                    .combinedClickable(
                        onClick = { onClick() },
                        onLongClick = { onLongPress() }
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_folder_24),
                    contentDescription = "folder",
                    tint = getTint(itemColors = ItemColor.values(), selectedColor = folder.colorIndex),
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(32.dp)
                )
                Text(
                    text = folder.title,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            }
            if (folder.isPinned) {
                Icon(
                    painter = painterResource(R.drawable.ic_pin_24),
                    contentDescription = "pin",
                    tint = MaterialTheme.colors.onSurface.copy(0.5f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(12.dp)
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

@Composable
private fun getTint(
    itemColors: Array<ItemColor>,
    selectedColor: Int
): Color {
    return if (selectedColor!=1){
        Color(itemColors[selectedColor].color).copy(0.8f)
    } else{
        YellowFolder
    }
}
