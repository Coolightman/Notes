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
        R.drawable.ic_note_24,
        NavRoutes.Notes.route
    ),

    TASKS(
        R.string.tasks_title,
        R.drawable.ic_task_24,
        NavRoutes.Tasks.route
    )
}
