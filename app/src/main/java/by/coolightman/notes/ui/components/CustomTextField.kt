package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    fontSize: TextUnit = 18.sp,
    keyboardController: SoftwareKeyboardController?,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    BasicTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        textStyle = MaterialTheme.typography.body1.copy(
            fontSize = fontSize,
            color = LocalContentColor.current.copy(LocalContentAlpha.current)
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.body1.copy(fontSize = fontSize),
                        color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                    )
                }
            }
            innerTextField()
        },
        onTextLayout = { onTextLayout(it) },
        modifier = modifier
            .focusRequester(focusRequester)
    )
}