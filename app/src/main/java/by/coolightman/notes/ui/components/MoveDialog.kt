package by.coolightman.notes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.ui.theme.InactiveBackground
import by.coolightman.notes.util.isDarkMode

@Composable
fun MoveDialog(
    modifier: Modifier = Modifier,
    isMainScreen: Boolean = false,
    destinations: List<Folder>,
    onClickFolder: (Long) -> Unit,
    onCancel: () -> Unit
) {

    val listState = rememberLazyListState()

    AlertDialog(
        onDismissRequest = { onCancel() },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.move_to),
                    color = MaterialTheme.colors.onSurface.copy(0.8f),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (!isMainScreen) {
                            item {
                                FolderCard(
                                    title = "Main screen",
                                    folderId = 0L,
                                    icon = painterResource(id = R.drawable.ic_note_24),
                                    onClick = { onClickFolder(it) }
                                )
                            }
                        }
                        items(items = destinations, key = { it.id }) { folder ->
                            FolderCard(
                                title = folder.title,
                                folderId = folder.id,
                                onClick = { onClickFolder(it) }
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    TextButton(
                        onClick = { onCancel() },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = InactiveBackground,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = if (isDarkMode()) MaterialTheme.colors.secondary
        else MaterialTheme.colors.background,
        modifier = modifier
    )
}

@Composable
private fun FolderCard(
    title: String,
    folderId: Long,
    icon: Painter = painterResource(id = R.drawable.ic_baseline_folder_24),
    onClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clickable { onClick(folderId) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = icon,
                contentDescription = "icon",
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 12.dp)
            )
        }
    }
}
