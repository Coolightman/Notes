package by.coolightman.notes.ui.model

sealed class NavRoute(val route: String) {
    object Notes : NavRoute("notes")
    object EditNote : NavRoute("edit_note")
    object NotesTrash : NavRoute("notes_trash")
    object Tasks : NavRoute("tasks")
    object EditTask : NavRoute("edit_task")
    object Settings : NavRoute("settings")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
