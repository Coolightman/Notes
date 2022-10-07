package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.SortNotesBy
import by.coolightman.notes.ui.theme.InactiveBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SortByChipDouble(
    onSort: (SortNotesBy) -> Unit,
    currentSortIndex: Int,
    text: String,
    chipIndex1: Int,
    chipIndex2: Int
) {
    FilterChip(
        onClick = {
            if (currentSortIndex == chipIndex1) onSort(SortNotesBy.values()[chipIndex2])
            else onSort(SortNotesBy.values()[chipIndex1])
        },
        content = { Text(text = text) },
        selected = currentSortIndex == chipIndex1 || currentSortIndex == chipIndex2,
        trailingIcon = {
            Icon(
                painter =
                if (currentSortIndex == chipIndex2) painterResource(R.drawable.ic_baseline_north_24)
                else painterResource(R.drawable.ic_baseline_south_24),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = MaterialTheme.colors.primary.copy(0.5f),
            selectedLeadingIconColor = MaterialTheme.colors.onSurface,
            selectedContentColor = MaterialTheme.colors.onSurface,
            backgroundColor = InactiveBackground.copy(0.3f)
        ),
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}