package by.coolightman.notes.data.local.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = NoteDb::class)
@Entity(tableName = "notesFts")
data class NoteFtsDb(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId: Int,
    val title: String,
    val text: String,
)
