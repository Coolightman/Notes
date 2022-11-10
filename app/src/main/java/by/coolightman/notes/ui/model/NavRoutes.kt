package by.coolightman.notes.ui.model

sealed class NavRoutes(val route: String) {
    object Splash : NavRoutes("splash")
    object Notes : NavRoutes("notes")
    object EditNote : NavRoutes("edit_note")
    object NotesTrash : NavRoutes("notes_trash")
    object SearchNote : NavRoutes("search_note")
    object Tasks : NavRoutes("tasks")
    object EditTask : NavRoutes("edit_task")
    object SearchTask : NavRoutes("search_task")
    object Settings : NavRoutes("settings")
    object EditFolder : NavRoutes("edit_folder")
    object IntoFolder : NavRoutes("into_folder")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
