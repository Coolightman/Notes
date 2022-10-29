package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.util.isDarkMode

@Composable
fun NotificationsRepeatDialog(
    modifier: Modifier = Modifier,
    selectedType: RepeatType,
    confirmButtonText: String,
    confirmButtonColor: Color = MaterialTheme.colors.primary,
    onConfirm: (Int) -> Unit,
    onCancel: () -> Unit
) {
    var selected by remember {
        mutableStateOf(selectedType.ordinal)
    }
    val repeatTypes by remember {
        mutableStateOf(RepeatType.values())
    }
    AlertDialog(
        onDismissRequest = { onCancel() },
        buttons = {
            Column(modifier = modifier.fillMaxWidth()) {
                repeatTypes.forEach {
                    Row(
                        verticalAlignment = CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = it.ordinal == selected,
                                onClick = { selected = it.ordinal },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = it.ordinal == selected,
                            onClick = { selected = it.ordinal },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )
                        Text(
                            text = stringResource(it.text),
                            style = MaterialTheme.typography.body1.merge(),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    TextButton(
                        onClick = { onConfirm(selected) }, modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = confirmButtonText,
                            color = confirmButtonColor,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (isDarkMode()) MaterialTheme.colors.secondary
        else MaterialTheme.colors.background
    )
}
