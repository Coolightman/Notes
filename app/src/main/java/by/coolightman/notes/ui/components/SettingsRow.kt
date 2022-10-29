package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import by.coolightman.notes.util.isDarkMode

@Composable
fun SettingsRow(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp, 1.dp, 4.dp, 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDarkMode()) MaterialTheme.colors.primary
                    else MaterialTheme.colors.primaryVariant
                ),
                modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 0.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 12.dp)
            ) {
                content()
            }
        }
    }
}
