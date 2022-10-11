package by.coolightman.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.ui.model.ItemColor
import kotlinx.coroutines.launch

@Composable
fun NotesSortFilterPanel(
    isVisible: Boolean = false,
    currentSortIndex: Int,
    onSort: (SortBy) -> Unit,
    currentFilterSelection: List<Boolean>,
    onFilterSelection: (List<Boolean>) -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val colors = remember { ItemColor.values() }
    var isFilterDropMenuExpanded by remember {
        mutableStateOf(false)
    }
    var filterSelectionList by remember {
        mutableStateOf(colors.map { false })
    }
    LaunchedEffect(currentFilterSelection) {
        filterSelectionList = currentFilterSelection
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(
            animationSpec = tween(
                easing = LinearOutSlowInEasing
            )
        ),
        exit = shrinkVertically(
            animationSpec = tween(
                easing = FastOutLinearInEasing
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .background(MaterialTheme.colors.secondary)
            ) {
                SortByChipDouble(
                    onSort = { onSort(it) },
                    currentSortIndex = currentSortIndex,
                    text = stringResource(R.string.color),
                    chipIndex1 = SortBy.COLOR.ordinal,
                    chipIndex2 = SortBy.COLOR_DESC.ordinal
                )
                SortByChipDouble(
                    onSort = { onSort(it) },
                    currentSortIndex = currentSortIndex,
                    text = stringResource(R.string.created_sort),
                    chipIndex1 = SortBy.CREATE_DATE.ordinal,
                    chipIndex2 = SortBy.CREATE_DATE_DESC.ordinal
                )
                SortByChipDouble(
                    onSort = { onSort(it) },
                    currentSortIndex = currentSortIndex,
                    text = stringResource(R.string.edited_sort),
                    chipIndex1 = SortBy.EDIT_DATE.ordinal,
                    chipIndex2 = SortBy.EDIT_DATE_DESC.ordinal
                )
                FilterByColorChip(
                    isSelected = isFilterDropMenuExpanded,
                    isActive = currentFilterSelection.contains(true),
                    onClick = {
                        scope.launch {
                            scrollState.scrollTo(scrollState.maxValue)
                            isFilterDropMenuExpanded = !isFilterDropMenuExpanded
                        }
                    }
                ) {
                    DropdownMenu(
                        expanded = isFilterDropMenuExpanded,
                        onDismissRequest = { isFilterDropMenuExpanded = false },
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        ResetColorFilterButton(
                            onClick = {
                                if (filterSelectionList.contains(true)) {
                                    filterSelectionList = colors.map { false }
                                    onFilterSelection(filterSelectionList)
                                }
                            }
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                        )
                        colors.forEachIndexed { index, itemColor ->
                            ColorFilterButton(
                                itemColor = itemColor,
                                isSelected = filterSelectionList[index],
                                onClick = {
                                    filterSelectionList = mutableListOf<Boolean>().apply {
                                        filterSelectionList.forEachIndexed { index, b ->
                                            if (index == it) {
                                                add(!b)
                                            } else add(b)
                                        }
                                    }
                                    onFilterSelection(filterSelectionList)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}