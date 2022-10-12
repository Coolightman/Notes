package by.coolightman.notes.data.local.dbModel

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = NoteDb::class)
@Entity(tableName = "notesFts")
data class NoteFtsDb(
    val title: String,
    val text: String,
)
