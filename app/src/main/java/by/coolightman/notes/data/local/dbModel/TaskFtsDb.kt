package by.coolightman.notes.data.local.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = TaskDb::class)
@Entity(tableName = "tasksFts")
data class TaskFtsDb(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId: Int,
    val text: String,
)
