package by.coolightman.notes.ui.model

sealed class Screen(val route: String) {
    object Notes : Screen("notes")
    object EditNote : Screen("edit_note")
    object NotesTrash : Screen("notes_trash")
    object Tasks : Screen("tasks")
    object EditTask : Screen("edit_task")
    object Settings: Screen("settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
