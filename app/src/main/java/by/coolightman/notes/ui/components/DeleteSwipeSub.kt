package by.coolightman.notes.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R

private const val ANIMATE_DURATION = 400

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteSwipeSub(
    dismissState: DismissState
) {
    val background by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent
            else -> Color.Red.copy(0.2f)
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    val scale by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 1.2f else 1.4f,
        animationSpec = tween(ANIMATE_DURATION)
    )

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        background
                    ),
                    startX = 50f,
                    endX = 400f
                )
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete_sweep_24),
            contentDescription = "delete action",
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .scale(scaleX = -1f, scaleY = 1f)
                .scale(scale)
        )
    }

}