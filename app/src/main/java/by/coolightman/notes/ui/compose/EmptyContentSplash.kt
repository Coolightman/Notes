package by.coolightman.notes.ui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            val hasIcon = remember {
                derivedStateOf { iconId != 0 }
            }
            val hasText = remember {
                derivedStateOf { textId != 0 }
            }

            if (hasIcon.value) {
                Icon(
                    painter = painterResource(iconId),
                    contentDescription = "splash",
                    tint = color
                )
            }

            if (hasText.value) {
                Text(
                    text = stringResource(textId),
                    style = MaterialTheme.typography.subtitle2,
                    color = color
                )
            }
        }
    }

}