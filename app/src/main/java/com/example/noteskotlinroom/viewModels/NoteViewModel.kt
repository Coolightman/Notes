package com.example.noteskotlinroom.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.noteskotlinroom.databases.NotesDatabase
import com.example.noteskotlinroom.entities.Note
import java.util.concurrent.Executors

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val db: NotesDatabase = NotesDatabase.getDatabase(application)
    private val notes: LiveData<List<Note>> = db.noteDao().getAll()

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun deleteNote(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            db.noteDao().deleteAll(note)
        }
    }

    fun insertNote(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            db.noteDao().insertAll(note)
        }
    }

    fun updateNote(note: Note) {
        Executors.newSingleThreadExecutor().execute {
            db.noteDao().updateAll(note)
        }
    }


}