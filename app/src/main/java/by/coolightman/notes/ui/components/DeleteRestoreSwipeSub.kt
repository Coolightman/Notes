package by.coolightman.notes.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R

private const val ANIMATE_DURATION = 400
private const val GRADIENT_START = 20f
private const val GRADIENT_END = 700f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteRestoreSwipeSub(
    dismissState: DismissState
) {

    var backgroundColor by remember {
        mutableStateOf(Color.Transparent)
    }

    var brushStartOffset by remember {
        mutableStateOf(Offset(GRADIENT_START, 0f))
    }

    var brushEndOffset by remember {
        mutableStateOf(Offset(GRADIENT_END, 0f))
    }

    var rowXSize by remember {
        mutableStateOf(0)
    }

    var deleteIconTint by remember {
        mutableStateOf(Color.Transparent)
    }

    var restoreIconTint by remember {
        mutableStateOf(Color.Transparent)
    }

    when (dismissState.targetValue) {
        DismissValue.DismissedToStart -> {
            backgroundColor = Color.Green.copy(0.2f)
            brushStartOffset = Offset(rowXSize - GRADIENT_START, 0f)
            brushEndOffset = Offset(rowXSize - GRADIENT_END, 0f)
            deleteIconTint = Color.Transparent
            restoreIconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
        DismissValue.DismissedToEnd -> {
            backgroundColor = Color.Red.copy(0.2f)
            brushStartOffset = Offset(GRADIENT_START, 0f)
            brushEndOffset = Offset(GRADIENT_END, 0f)
            deleteIconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            restoreIconTint = Color.Transparent
        }
        DismissValue.Default ->{
            backgroundColor = Color.Transparent
            restoreIconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            deleteIconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }

    val background by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(ANIMATE_DURATION)
    )

    val scale by animateFloatAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> 1.2f
            else -> 1.4f
        },
        animationSpec = tween(ANIMATE_DURATION)
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates -> rowXSize = coordinates.size.width }
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        background
                    ),
                    start = brushStartOffset,
                    end = brushEndOffset
                )
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete_forever_24),
            contentDescription = "delete action",
            tint = deleteIconTint,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .scale(scaleX = -1f, scaleY = 1f)
                .scale(scale)
        )

        Icon(
            painter = painterResource(R.drawable.ic_restore_from_trash_24),
            contentDescription = "restore action",
            tint = restoreIconTint,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .scale(scale)
        )
    }

}

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
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> 1.2f
            else -> 1.4f
        },
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
                    startX = GRADIENT_START,
                    endX = GRADIENT_END
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