package by.coolightman.notes.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.ui.model.ItemColors
import by.coolightman.notes.util.toFormattedDate

@Composable
fun NotesItem(
    item: Note,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = elevation,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            if (item.title.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(ItemColors.values()[item.colorIndex].color).copy(0.8f))
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = item.title,
                        style = MaterialTheme.typography.h6.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .align(Alignment.Center)
                    )
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(ItemColors.values()[item.colorIndex].color).copy(0.8f))
                        .height(4.dp)
                )
            }
            Card(
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(ItemColors.values()[item.colorIndex].color).copy(0.05f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
                    ) {
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.body1.copy(fontSize = 18.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(12.dp, 8.dp, 12.dp, 0.dp)
                        )
                    }

                    val dateText = if (item.isEdited) {
                        val edited = stringResource(R.string.edited)
                        "$edited " + item.editedAt.toFormattedDate()
                    } else item.createdAt.toFormattedDate()
                    DateText(text = dateText)
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun NotesItemPreview() {
    val note = Note(
        title = "Title",
        text = "Text text text Text text text \nText text text",
        colorIndex = 1,
        createdAt = 19871988977L,
        editedAt = 198719889775L,
        isShowDate = true,
        isEdited = false,
        isInTrash = false
    )
    NotesItem(item = note, onClick = {})
}