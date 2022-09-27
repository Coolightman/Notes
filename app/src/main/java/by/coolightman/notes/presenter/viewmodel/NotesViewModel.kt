package by.coolightman.notes.presenter.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import by.coolightman.notes.presenter.state.NotesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(

) : ViewModel() {

    var state by mutableStateOf(NotesScreenState())
        private set

    fun onAction() {
        Log.d("NotesViewModel", "onAction")
    }
}