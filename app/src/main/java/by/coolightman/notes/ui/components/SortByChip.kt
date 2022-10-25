package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.ui.theme.InactiveBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SortByChipDouble(
    onSort: (SortBy) -> Unit,
    currentSortIndex: Int,
    text: String,
    chipIndex1: Int,
    chipIndex2: Int
) {
    FilterChip(
        onClick = {
            if (currentSortIndex == chipIndex1) onSort(SortBy.values()[chipIndex2])
            else onSort(SortBy.values()[chipIndex1])
        },
        content = { Text(text = text) },
        selected = currentSortIndex == chipIndex1 || currentSortIndex == chipIndex2,
        trailingIcon = {
            Icon(
                painter =
                if (currentSortIndex == chipIndex2) painterResource(R.drawable.ic_round_north_24)
                else painterResource(R.drawable.ic_round_south_24),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = MaterialTheme.colors.primary.copy(0.7f),
            selectedLeadingIconColor = MaterialTheme.colors.onSurface,
            selectedContentColor = MaterialTheme.colors.onSurface,
            backgroundColor = InactiveBackground.copy(0.3f)
        ),
        modifier = Modifier.padding(
            end = 4.dp,
            start = if (chipIndex1 == 0) 8.dp
            else 4.dp
        )
    )
}
