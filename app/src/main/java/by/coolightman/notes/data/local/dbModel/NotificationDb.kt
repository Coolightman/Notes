package by.coolightman.notes.data.local.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationDb (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "task_id")
    val taskId: Long,
    val time: Long,
    @ColumnInfo(name = "repeat_type")
    val repeatType: Int
)