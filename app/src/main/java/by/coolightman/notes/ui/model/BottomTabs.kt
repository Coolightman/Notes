package by.coolightman.notes.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.coolightman.notes.R

enum class BottomTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    NOTES(
        R.string.notes_title,
        R.drawable.ic_outline_note_64,
        NavRoutes.Notes.route
    ),

    TASKS(
        R.string.tasks_title,
        R.drawable.ic_baseline_task_alt_64,
        NavRoutes.Tasks.route
    )
}


