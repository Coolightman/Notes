package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BadgedIcon(
    icon: Painter,
    iconEmptyBadge: Painter? = null,
    badgeValue: Int,
    badgeColor: Color = MaterialTheme.colors.secondary.copy(0.7f),
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() }
    ) {
        if (badgeValue > 0) {
            Icon(
                painter = icon,
                contentDescription = "full trash"
            )
            Box(
                modifier = Modifier
                    .offset(x = 16.dp, y = (-16).dp)
                    .defaultMinSize(16.dp, 16.dp)
                    .background(
                        color = badgeColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = badgeValue.toString(),
                    style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Icon(
                painter = iconEmptyBadge ?: icon,
                contentDescription = "full trash"
            )
        }
    }
}