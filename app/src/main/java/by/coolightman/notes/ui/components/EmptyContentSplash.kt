package by.coolightman.notes.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun EmptyContentSplash(
    @DrawableRes iconId: Int = 0,
    @StringRes textId: Int = 0,
    color: Color = MaterialTheme.colors.onSurface.copy(0.5f)
) {
    var isSplashVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(200)
        isSplashVisible = true
    }

    val animatedColor by animateColorAsState(targetValue = color)

    if (isSplashVisible) {
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
                        tint = animatedColor,
                        modifier = Modifier.size(64.dp)
                    )
                }

                if (hasText.value) {
                    Text(
                        text = stringResource(textId),
                        style = MaterialTheme.typography.subtitle2,
                        color = animatedColor
                    )
                }
            }
        }
    }
}
