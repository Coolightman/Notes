package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateText(text: String) {
    Text(
        textAlign = TextAlign.End,
        text = text,
        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
        color = MaterialTheme.colors.onSurface.copy(0.25f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp)
    )
}