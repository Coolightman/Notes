package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import by.coolightman.notes.domain.model.RemindType
import by.coolightman.notes.ui.theme.InactiveBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RemindAdvanceChip(
    remindTypes: List<Boolean>,
    chipRemindType: RemindType,
    onClick: (List<Boolean>) -> Unit
) {
    FilterChip(
        selected = remindTypes[chipRemindType.ordinal],
        onClick = {
            val list = mutableListOf<Boolean>()
            remindTypes.forEachIndexed { index, b ->
                if (index == chipRemindType.ordinal) {
                    list.add(!b)
                } else list.add(b)
            }
            onClick(list)
        },
        content = {
            Text(
                text = chipRemindType.minutes.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = MaterialTheme.colors.primary.copy(0.7f),
            selectedLeadingIconColor = MaterialTheme.colors.onSurface,
            selectedContentColor = MaterialTheme.colors.onSurface,
            backgroundColor = InactiveBackground.copy(0.3f)
        ),
        modifier = Modifier
            .height(30.dp)
            .width(50.dp)
    )
}
