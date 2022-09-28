package by.coolightman.notes.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.coolightman.notes.R

@Composable
fun NotesScreenSplash(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            EmptyContentSplash(
                iconId = R.drawable.ic_outline_note_64,
                textId = R.string.notes_title
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    NotesScreenSplash()
}