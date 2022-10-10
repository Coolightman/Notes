package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import by.coolightman.notes.ui.model.ItemColor

@Composable
fun ColorFilterButton(
    itemColor: ItemColor,
    isSelected: Boolean = false,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color(itemColor.color).copy(0.8f))
            .clickable { onClick(itemColor.ordinal) }
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(4.dp))
}