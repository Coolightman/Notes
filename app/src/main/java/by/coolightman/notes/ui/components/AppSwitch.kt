package by.coolightman.notes.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import by.coolightman.notes.ui.theme.InactiveBackground


@Composable
fun AppSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = {
            onCheckedChange()
        },
        modifier = modifier,
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = InactiveBackground,
            uncheckedTrackColor = InactiveBackground,
            checkedThumbColor = MaterialTheme.colors.primary,
            checkedTrackColor = MaterialTheme.colors.primary
        )
    )
}