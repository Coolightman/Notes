package by.coolightman.notes.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import by.coolightman.notes.data.local.dao.FolderDao
import by.coolightman.notes.data.local.dao.NoteDao
import by.coolightman.notes.data.local.dao.NotificationDao
import by.coolightman.notes.data.local.dao.TaskDao
import by.coolightman.notes.data.local.dbModel.FolderDb
import by.coolightman.notes.data.local.dbModel.NoteDb
import by.coolightman.notes.data.local.dbModel.NoteFtsDb
import by.coolightman.notes.data.local.dbModel.NotificationDb
import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.data.local.dbModel.TaskFtsDb

@Database(
    version = 4,
    entities = [
        NoteDb::class,
        TaskDb::class,
        NotificationDb::class,
        NoteFtsDb::class,
        TaskFtsDb::class,
        FolderDb::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AppDatabase.MyAutoMigrationTo2::class),
        AutoMigration(from = 2, to = 3, spec = AppDatabase.MyAutoMigrationTo3::class),
        AutoMigration(from = 3, to = 4)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    @DeleteColumn(tableName = "tasks", columnName = "notification_time")
    @DeleteColumn(tableName = "tasks", columnName = "is_has_notification")
    class MyAutoMigrationTo2 : AutoMigrationSpec

    @DeleteColumn(tableName = "tasks", columnName = "is_selected")
    @DeleteColumn(tableName = "notes", columnName = "is_selected")
    class MyAutoMigrationTo3 : AutoMigrationSpec

    abstract fun noteDao(): NoteDao

    abstract fun taskDao(): TaskDao

    abstract fun notificationDao(): NotificationDao

    abstract fun folderDao(): FolderDao
}
