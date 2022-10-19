package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.ui.theme.InactiveBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterByColorChip(
    isSelected: Boolean = false,
    isActive: Boolean = false,
    onClick: () -> Unit,
    dropdown: @Composable () -> Unit
) {
    FilterChip(
        onClick = { onClick() },
        content = {
            Text(text = stringResource(R.string.filter))
            dropdown()
        },
        selected = isSelected,
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_palette_24),
                contentDescription = null,
                tint = if (!isSelected && isActive) MaterialTheme.colors.primary.copy(0.5f)
                else LocalContentColor.current,
                modifier = Modifier.size(16.dp)
            )
        },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = MaterialTheme.colors.primary.copy(0.5f),
            selectedLeadingIconColor = MaterialTheme.colors.onSurface,
            selectedContentColor = MaterialTheme.colors.onSurface,
            backgroundColor = InactiveBackground.copy(0.3f),
        ),
        modifier = Modifier.padding(start = 4.dp, end = 8.dp)
    )
}
