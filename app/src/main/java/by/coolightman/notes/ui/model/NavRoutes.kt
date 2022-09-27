package by.coolightman.notes.ui.model

sealed class NavRoutes(val route: String) {
    object Notes : NavRoutes("notes")
    object EditNote : NavRoutes("edit_note")
    object NotesTrash : NavRoutes("notes_trash")
    object Tasks : NavRoutes("tasks")
    object EditTask : NavRoutes("edit_task")
    object Settings: NavRoutes("settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
