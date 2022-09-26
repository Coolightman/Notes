package by.coolightman.notes.data.database.dbModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val text: String,
    val color: Long,
    val createdAt: Long,
    val editedAt: Long,
    val isShowDate: Boolean,
    val isEdited: Boolean,
    val isInTrash: Boolean
)
