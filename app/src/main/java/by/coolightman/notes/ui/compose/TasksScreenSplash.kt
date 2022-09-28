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
fun TasksScreenSplash(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            EmptyContentSplash(
                iconId = R.drawable.ic_baseline_task_alt_64,
                textId = R.string.tasks_title
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    TasksScreenSplash()
}