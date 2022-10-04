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
            SortByChipDouble(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.color),
                chipIndex1 = SortNotesBy.COLOR.ordinal,
                chipIndex2 = SortNotesBy.COLOR_DESC.ordinal
            )
            SortByChipDouble(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.created_sort),
                chipIndex1 = SortNotesBy.CREATE_DATE.ordinal,
                chipIndex2 = SortNotesBy.CREATE_DATE_DESC.ordinal
            )
            SortByChipDouble(
                onSort = { onSort(it) },
                currentSortIndex = currentSortIndex,
                text = stringResource(R.string.edited_sort),
                chipIndex1 = SortNotesBy.EDIT_DATE.ordinal,
                chipIndex2 = SortNotesBy.EDIT_DATE_DESC.ordinal
            )
        }
    }
}