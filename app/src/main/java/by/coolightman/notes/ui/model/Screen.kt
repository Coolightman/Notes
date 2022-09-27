package by.coolightman.notes.ui.model

sealed class Screen(val route: String){
    object Notes: Screen("notes")
    object EditNote: Screen("edit_note")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
