package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import by.coolightman.notes.domain.model.SortNotesBy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SortByChip(
    onSort: (SortNotesBy) -> Unit,
    currentSortIndex: Int,
    text: String,
    trailingIcon: Painter,
    chipIndex: Int
) {
    FilterChip(
        onClick = {
            onSort(SortNotesBy.values()[chipIndex])
        },
        content = { Text(text = text) },
        selected = currentSortIndex == chipIndex,
        trailingIcon = {
            Icon(
                painter = trailingIcon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}