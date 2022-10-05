package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.*
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
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp)
        ) {
            Text(text = label)
            Text(
                text = value.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(28.dp)
            )
        }
    }
}