package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R

@Composable
fun NoteTitleField(
    title: String,
    maxLength: Int = 20,
    fontSize: TextUnit = 18.sp,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager
) {

    val textStyle = MaterialTheme.typography.h6.copy(
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )

    Box(
        Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        BasicTextField(
            value = title,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            singleLine = true,
            textStyle = textStyle.copy(
                textAlign = TextAlign.Center,
                color = LocalContentColor.current.copy(LocalContentAlpha.current)
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (title.isEmpty()) {
                        Text(
                            text = stringResource(R.string.title_placeholder),
                            style = textStyle,
                            color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                        )
                    }
                }
                innerTextField()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}