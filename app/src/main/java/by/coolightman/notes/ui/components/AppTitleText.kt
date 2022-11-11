package by.coolightman.notes.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun AppTitleText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h5.copy(
            fontWeight = FontWeight.Light,
            letterSpacing = 1.0.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.onSecondary
    )
}
