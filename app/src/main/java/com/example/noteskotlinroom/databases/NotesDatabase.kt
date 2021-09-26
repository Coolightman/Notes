package com.example.noteskotlinroom.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteskotlinroom.interfaces.NoteDao
import com.example.noteskotlinroom.models.Note

@Database(
    version = 1,
    entities = [Note::class]
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}