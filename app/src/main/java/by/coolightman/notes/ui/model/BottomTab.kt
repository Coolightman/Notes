package by.coolightman.notes.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.coolightman.notes.R

sealed class BottomTab(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    object Notes : BottomTab(
        R.string.notes_title,
        R.drawable.ic_outline_note_64,
        NavRoute.Notes.route
    )

    object Tasks : BottomTab(
        R.string.tasks_title,
        R.drawable.ic_baseline_task_alt_64,
        NavRoute.Tasks.route
    )
}


