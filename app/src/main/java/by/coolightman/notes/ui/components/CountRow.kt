package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CountRow(
    modifier: Modifier = Modifier,
    label: String,
    value: Int
) {
    if (value != 0) {
        Row(modifier = modifier) {
            Text(text = label)
            Text(
                text = value.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(28.dp)
            )
        }
    }
}