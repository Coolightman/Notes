package by.coolightman.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.ui.theme.InactiveBackground
import by.coolightman.notes.util.isDarkMode

@Composable
fun CreateFolderDialog(
    modifier: Modifier = Modifier,
    onCreate: (String) -> Unit,
    onCancel: () -> Unit,
) {

    var folderTitle by remember {
        mutableStateOf("")
    }

    var isErrorVisible by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = { onCancel() },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.new_folder),
                    color = MaterialTheme.colors.onSurface.copy(0.8f),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 0.dp)
                )

                OutlinedTextField(
                    value = folderTitle,
                    onValueChange = {
                        folderTitle = it
                        if (isErrorVisible) {
                            isErrorVisible = false
                        }
                    },
                    placeholder = { Text(text = stringResource(R.string.title_placeholder)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp, 12.dp, 0.dp)
                )
                AnimatedVisibility(
                    visible = isErrorVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_title),
                        color = MaterialTheme.colors.error,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    )
                }

                AnimatedVisibility(
                    visible = !isErrorVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 12.dp)
                ) {
                    TextButton(
                        onClick = { onCancel() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = InactiveBackground,
                            style = MaterialTheme.typography.button
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .width(0.5.dp)
                            .fillMaxHeight(0.7f)
                            .background(InactiveBackground.copy(0.5f))
                    )
                    TextButton(
                        onClick = {
                            if (folderTitle.isEmpty()) {
                                isErrorVisible = true
                            } else {
                                onCreate(folderTitle.trim())
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.create),
                            color = MaterialTheme.colors.primary,
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
