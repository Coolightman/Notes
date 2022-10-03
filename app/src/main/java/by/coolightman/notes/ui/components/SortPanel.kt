package by.coolightman.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.SortNotesBy

@Composable
fun SortPanel(
    isVisible: Boolean = false,
    currentSortIndex: Int,
    onSort: (SortNotesBy) -> Unit
) {
    val scrollState = rememberScrollState()
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(
            animationSpec = tween(
                durationMillis = 250,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = 150,
                easing = FastOutLinearInEasing
            )
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            SortByChip(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.color),
                trailingIcon = painterResource(R.drawable.ic_baseline_south_24),
                chipIndex = SortNotesBy.COLOR.ordinal
            )
            SortByChip(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.color),
                trailingIcon = painterResource(R.drawable.ic_baseline_north_24),
                chipIndex = SortNotesBy.COLOR_DESC.ordinal
            )
            SortByChip(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.created_sort),
                trailingIcon = painterResource(R.drawable.ic_baseline_south_24),
                chipIndex = SortNotesBy.CREATE_DATE.ordinal
            )
            SortByChip(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.created_sort),
                trailingIcon = painterResource(R.drawable.ic_baseline_north_24),
                chipIndex = SortNotesBy.CREATE_DATE_DESC.ordinal
            )
            SortByChip(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.edited_sort),
                trailingIcon = painterResource(R.drawable.ic_baseline_south_24),
                chipIndex = SortNotesBy.EDIT_DATE.ordinal
            )
            SortByChip(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.edited_sort),
                trailingIcon = painterResource(R.drawable.ic_baseline_north_24),
                chipIndex = SortNotesBy.EDIT_DATE_DESC.ordinal
            )
        }
    }
}