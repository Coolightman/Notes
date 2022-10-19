package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable

@Composable
fun SelectionTopAppBar(
    onCloseClick: () -> Unit,
    selectedCount: Int,
    actions: @Composable (RowScope.() -> Unit)
) {
    AppTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onCloseClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colors.onSurface.copy(alpha = LocalContentAlpha.current)
                )
            }
        },
        title = {
            AppTitleText(text = "$selectedCount")
        },
        actions = {
            actions()
        }
    )
}
