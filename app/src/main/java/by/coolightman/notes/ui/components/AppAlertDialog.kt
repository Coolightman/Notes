package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.coolightman.notes.R

@Composable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    text: String,
    confirmButtonText: String,
    cancelButtonText: String = stringResource(R.string.cancel),
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        text = {
            Text(text = text)
        },
        buttons = {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { onCancel() }) {
                    Text(text = cancelButtonText)
                }
                TextButton(onClick = { onConfirm() }) {
                    Text(text = confirmButtonText)
                }
            }
        },
        modifier = modifier
    )
}