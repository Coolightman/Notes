package by.coolightman.notes.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import by.coolightman.notes.ui.compose.PrepareUI
import by.coolightman.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                PrepareUI {

                }
            }
        }
    }
}
