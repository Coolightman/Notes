package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.RemindType
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.ui.theme.NotesTheme
import by.coolightman.notes.util.getLocalRoundedCalendarInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddNotificationContent(
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    scaffoldState: ScaffoldState,
//    viewModel: EditTaskViewModel
) {

    var calendar by remember {
        mutableStateOf(getLocalRoundedCalendarInstance())
    }
    var openTimePicker by remember {
        mutableStateOf(false)
    }
    if (openTimePicker) {
        TimePicker(
            calendar = calendar,
            onCancel = { openTimePicker = false },
            selectedTime = { calendar = it }
        )
    }

    var openDatePicker by remember {
        mutableStateOf(false)
    }
    if (openDatePicker) {
        DatePicker(
            calendar = calendar,
            onCancel = { openDatePicker = false },
            selectedTime = { calendar = it }
        )
    }

    var repeatType by remember {
        mutableStateOf(RepeatType.NO)
    }
    var openRepeatTypeDialog by remember {
        mutableStateOf(false)
    }
    if (openRepeatTypeDialog) {
        NotificationsRepeatDialog(
            confirmButtonText = stringResource(R.string.okay),
            onCancel = { openRepeatTypeDialog = false },
            onConfirm = {
                repeatType = RepeatType.values()[it]
                openRepeatTypeDialog = false
            }
        )
    }

    var remindTypes by remember {
        mutableStateOf(RemindType.values().map { false })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        NotificationDateTimeText(
            notificationDate = calendar.timeInMillis,
            onClickTime = { openTimePicker = true },
            onClickDate = { openDatePicker = true }
        )
        SettingsRow(
            title = stringResource(R.string.remind_in_advance),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            RemindAdvanceChip(
                remindTypes = remindTypes,
                chipRemindType = RemindType.FIVE_MIN,
                onClick = { remindTypes = it }
            )
            RemindAdvanceChip(
                remindTypes = remindTypes,
                chipRemindType = RemindType.TEN_MIN,
                onClick = { remindTypes = it }
            )
            RemindAdvanceChip(
                remindTypes = remindTypes,
                chipRemindType = RemindType.FIFTEEN_MIN,
                onClick = { remindTypes = it }
            )
            RemindAdvanceChip(
                remindTypes = remindTypes,
                chipRemindType = RemindType.TWENTY_MIN,
                onClick = { remindTypes = it }
            )
            RemindAdvanceChip(
                remindTypes = remindTypes,
                chipRemindType = RemindType.THIRTY_MIN,
                onClick = { remindTypes = it }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            DoneNotificationButton {
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun Preview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    NotesTheme {
        AddNotificationContent(
            scope = scope,
            bottomSheetState = modalBottomSheetState,
            scaffoldState = scaffoldState
        )
    }
}