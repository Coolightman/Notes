package by.coolightman.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import by.coolightman.notes.data.local.dao.NoteDao
import by.coolightman.notes.data.local.dao.TaskDao
import by.coolightman.notes.data.local.dbModel.NoteDb
import by.coolightman.notes.data.local.dbModel.NoteFtsDb
import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.data.local.dbModel.TaskFtsDb

@Database(
    version = 6,
    entities = [
        NoteDb::class,
        TaskDb::class,
        NoteFtsDb::class,
        TaskFtsDb::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    abstract fun taskDao(): TaskDao
}