package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.ui.theme.InactiveBackground

@Composable
fun AppAlertDialog(
    modifier: Modifier = Modifier,
    text: String,
    secondaryText: String = "",
    confirmButtonText: String,
    confirmButtonColor: Color = MaterialTheme.colors.primary,
    cancelButtonText: String = stringResource(R.string.cancel),
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = text,
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
                if (secondaryText.isNotEmpty()){
                    Text(
                        text = secondaryText,
                        color = MaterialTheme.colors.onSurface.copy(0.4f),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Light
                        ),
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 4.dp, 12.dp, 0.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 12.dp)
                ) {
                    TextButton(
                        onClick = { onCancel() }, modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = cancelButtonText,
                            color = InactiveBackground,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(0.5.dp)
                            .fillMaxHeight(0.7f)
                            .background(InactiveBackground.copy(0.5f))
                    )
                    TextButton(
                        onClick = { onConfirm() }, modifier = Modifier.weight(1f)
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
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = modifier
    )
}