package by.coolightman.notes.ui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import by.coolightman.notes.ui.theme.GrayContent

@Composable
fun EmptyContentSplash(
    @DrawableRes iconId: Int = 0,
    @StringRes textId: Int = 0,
    color: Color = GrayContent
) {

    if (iconId != 0) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = "splash",
            tint = color
        )
    }

    if (textId != 0) {
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.h5,
            color = color
        )
    }

}