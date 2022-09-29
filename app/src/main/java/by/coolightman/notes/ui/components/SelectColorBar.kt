package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.coolightman.notes.ui.model.ItemColors

@Composable
fun SelectColorBar(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    val colors = remember { ItemColors.values() }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        colors.forEachIndexed { index, itemColor ->
            Box(
                modifier = Modifier
                    .size(48.dp, 60.dp)
                    .clip(CircleShape)
                    .background(Color(itemColor.color))
                    .clickable { onSelect(index) }
            ){
                if (index == selected){
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectColorBarPreview() {
    var selectedColor by remember {
        mutableStateOf(0)
    }

    SelectColorBar(
        selected = selectedColor,
        onSelect = { selectedColor = it }
    )
}