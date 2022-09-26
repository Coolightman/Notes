package by.coolightman.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import by.coolightman.notes.data.database.dao.NoteDao
import by.coolightman.notes.data.database.dao.TaskDao
import by.coolightman.notes.data.database.dbModel.NoteDb
import by.coolightman.notes.data.database.dbModel.TaskDb

@Database(
    version = 1,
    entities = [
        NoteDb::class,
        TaskDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    abstract fun taskDao(): TaskDao
}