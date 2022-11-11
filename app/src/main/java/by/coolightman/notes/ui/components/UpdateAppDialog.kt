package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.ui.theme.InactiveBackground
import by.coolightman.notes.util.isDarkMode

@Composable
fun UpdateAppDialog(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onConfirm() },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.update),
                    color = MaterialTheme.colors.onSurface.copy(0.8f),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 0.dp)
                )
                Text(
                    text = stringResource(id = R.string.update_app_dialog_text),
                    color = MaterialTheme.colors.onSurface.copy(0.8f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 0.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 12.dp)
                ) {
                    TextButton(
                        onClick = { onConfirm() }, modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.okay),
                            color = InactiveBackground,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (isDarkMode()) MaterialTheme.colors.secondary
        else MaterialTheme.colors.background,
        modifier = modifier
    )
}

@Preview
@Composable
private fun Preview() {
    UpdateAppDialog {

    }
}
