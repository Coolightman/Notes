package by.coolightman.notes.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R

private const val ANIMATE_DURATION = 400
private const val GRADIENT_START = 10f
private const val GRADIENT_END = 600f
private const val COLOR_ALFA = 0.5f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteRestoreSwipeSub(
    dismissState: DismissState
) {

    val direction = dismissState.dismissDirection

    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.DismissedToEnd -> Color.Red.copy(COLOR_ALFA)
            DismissValue.DismissedToStart -> Color.Green.copy(COLOR_ALFA)
            DismissValue.Default -> Color.Transparent
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    val icon = when (direction) {
        DismissDirection.StartToEnd -> painterResource(R.drawable.ic_delete_forever_24)
        else -> painterResource(R.drawable.ic_restore_trash_24)
    }

    val alignment = when (direction) {
        DismissDirection.EndToStart -> Alignment.CenterEnd
        else -> Alignment.CenterStart
    }

    val iconScale by animateFloatAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> 1.2f
            else -> 1.4f
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    val iconTintAlfa by animateFloatAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> 0.2f
            else -> 0.6f
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    var rowXSize by remember {
        mutableStateOf(0)
    }

    val brushOffsetStart = when (direction) {
        DismissDirection.EndToStart -> Offset(rowXSize - GRADIENT_START, 0f)
        else -> Offset(GRADIENT_START, 0f)
    }

    val brushOffsetEnd = when (direction) {
        DismissDirection.EndToStart -> Offset(rowXSize - GRADIENT_END, 0f)
        else -> Offset(GRADIENT_END, 0f)
    }

    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        color
                    ),
                    start = brushOffsetStart,
                    end = brushOffsetEnd
                )
            )
            .onGloballyPositioned { coordinates -> rowXSize = coordinates.size.width }
            .padding(horizontal = 24.dp))
    {
        Icon(
            painter = icon,
            contentDescription = "icon",
            tint = LocalContentColor.current.copy(iconTintAlfa),
            modifier = Modifier.scale(iconScale)
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteSwipeSub(
    dismissState: DismissState,
    icon: Painter
) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent
            else -> Color.Red.copy(COLOR_ALFA)
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    val iconScale by animateFloatAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> 1.2f
            else -> 1.4f
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    val iconTintAlfa by animateFloatAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> 0.2f
            else -> 0.6f
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        color
                    ),
                    start = Offset(GRADIENT_START, 0f),
                    end = Offset(GRADIENT_END, 0f)
                )
            )
    )
    {
        Icon(
            painter = icon,
            contentDescription = "delete action",
            tint = LocalContentColor.current.copy(iconTintAlfa),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .scale(scaleX = -1f, scaleY = 1f)
                .scale(iconScale)
        )
    }
}